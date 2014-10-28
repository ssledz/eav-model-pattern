/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.eav.example;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.2
 */
public class Example {

	private static void help() {
		System.out.println("Usage:");
		System.out.println("java Example [ --ds (mysql|hsql) ] [ --help ]");
	}

	private static int indexOf(String[] args, String arg) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(arg)) {
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {

		if (args.length == 0) {
			hsqlExample();
			return;
		}

		int help = indexOf(args, "--help");
		int ds = indexOf(args, "--ds");

		if (ds == -1 || ds >= args.length || !Arrays.asList("mysql", "hsql").contains(args[ds + 1])) {
			help();
			return;
		}

		if (help >= 0) {
			help();
		}

		if (args[ds + 1].equals("mysql")) {
			example(MySqlDsConfig.class);
		} else {
			hsqlExample();
		}

	}

	public static void hsqlExample() throws Exception {
		example(HsqlDsConfig.class);
	}

	public static void mysqlExample() throws Exception {
		example(MySqlDsConfig.class);
	}

	public static void example(Class<?> dsConfig) throws Exception {

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class, dsConfig)) {

			BootstrapperService bootstrapper = ctx.getBean(BootstrapperService.class);

			bootstrapper.onApplicationStart();

			final DomainService domainService = ctx.getBean(DomainService.class);

			domainService.loadConfigurationFromFile("computer-person.eav");

			TransactionTemplate transactionTemplate = new TransactionTemplate(ctx.getBean(JpaTransactionManager.class));
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {

					Person gyles = domainService.findPersonByName("gyles");

					System.out.println("\n");
					System.out.println(Util.toString(gyles));

					System.out.println("\nGyles is getting older");
					gyles.setAge(gyles.getAge() + 1);
					Person emil = domainService.findPersonByName("emil");
					System.out.println("\nGyles met Emil and they become friends\n");
					emil.addFriend(gyles);
					gyles.addFriend(emil);

					System.out.println(Util.toString(gyles));
					System.out.println("\n");
					System.out.println(Util.toString(emil));
				}
			});

		}

	}

}
