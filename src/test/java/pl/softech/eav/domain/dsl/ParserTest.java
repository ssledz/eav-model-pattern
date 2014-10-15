package pl.softech.eav.domain.dsl;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

import pl.softech.eav.domain.dsl.Context;
import pl.softech.eav.domain.dsl.ContextVisitor;
import pl.softech.eav.domain.dsl.ContextVisitorAdapter;
import pl.softech.eav.domain.dsl.Parser;

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
