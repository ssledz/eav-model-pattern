package pl.softech.eav.domain.dsl;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

import pl.softech.eav.domain.dsl.Lexer;
import pl.softech.eav.domain.dsl.Token;
import pl.softech.eav.domain.dsl.Token.Type;

/**
 * @author ssledz
 */
public class LexerTest {

	//TODO implement
	@Test
	public void testNext() throws Exception {

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

		System.out.println(buffer);
		Lexer l = new Lexer(buffer.toString());

		while (true) {
			Token token = l.next();
			System.out.println(token);
			if (token.getType() == Type.EOF) {
				break;
			}
		}

	}

}
