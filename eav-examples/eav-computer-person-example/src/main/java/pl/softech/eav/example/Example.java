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

	public static void main(String[] args) throws Exception {

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {

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
