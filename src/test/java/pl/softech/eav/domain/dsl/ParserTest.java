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
package pl.softech.eav.domain.dsl;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

import pl.softech.eav.domain.dsl.Context;
import pl.softech.eav.domain.dsl.ContextVisitor;
import pl.softech.eav.domain.dsl.ContextVisitorAdapter;
import pl.softech.eav.domain.dsl.Parser;

/**
 * @author ssledz
 * @since 1.0
 */
public class ParserTest {
	//TODO implement
	@Test
	public void testParse() throws Exception {
		StringBuffer buffer = new StringBuffer();
		System.out.println();
		try (FileReader in = new FileReader(LexerTest.class.getResource("computer.conf").getFile())) {
			try (BufferedReader bin = new BufferedReader(in)) {
				String line;

				while ((line = bin.readLine()) != null) {
					buffer.append(line).append("\n");
				}
			}

		}
		
		ContextVisitor visitor = new ContextVisitorAdapter() {
			@Override
			protected void visitAny(Context ctx) {
				System.out.println(ctx.toString());
			}
		};
		Parser p = new Parser(visitor);
		p.parse(buffer.toString());
		
	}

}
