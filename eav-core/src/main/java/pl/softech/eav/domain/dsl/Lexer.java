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

import pl.softech.eav.domain.dsl.Token.Type;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class Lexer {

	private int current;
	private final String input;
	private long currentLineNumber = 1;

	Lexer(String input) {
		this.input = input;
	}

	private char getChar() {
		return input.charAt(current++);
	}

	private void unputChar() {
		current--;
	}

	private boolean hasNextChar() {
		return current < input.length();
	}

	Token next() {

		if (!hasNextChar()) {
			return new Token(Type.EOF, currentLineNumber);
		}

		char c = getChar();

		while (Character.isWhitespace(c)) {

			if(c == '\n') {
				currentLineNumber++;
			}
			
			if (!hasNextChar()) {
				return new Token(Type.EOF, currentLineNumber);
			}

			c = getChar();
		}

		if (c == '#') {
			while (c != '\n') {
				c = getChar();
			}
			currentLineNumber++;
			return next();
		}

		if (c == '"') {
			return nextString();
		}

		if (c == ':') {
			return new Token(Type.COLON, currentLineNumber);
		}

		unputChar();

		Token token = nextIdentifier();

		switch (token.getValue().toLowerCase()) {

		case "category":
			return new Token(Type.CATEGORY, currentLineNumber);
		case "attribute":
			return new Token(Type.ATTRIBUTE, currentLineNumber);
		case "object":
			return new Token(Type.OBJECT, currentLineNumber);
		case "name":
			return new Token(Type.NAME, currentLineNumber);
		case "data_type":
			return new Token(Type.DATA_TYPE, currentLineNumber);
		case "of":
			return new Token(Type.OF, currentLineNumber);
		case "end":
			return new Token(Type.END, currentLineNumber);
		case "dictionary":
			return new Token(Type.DICTIONARY, currentLineNumber);
		case "owner":
			return new Token(Type.OWNER, currentLineNumber);
		case "target":
			return new Token(Type.TARGET, currentLineNumber);
		case "relation":
			return new Token(Type.RELATION, currentLineNumber);
		case "relations":
			return new Token(Type.RELATIONS, currentLineNumber);
		}

		return token;

	}

	private void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
		if (!expression) {
			System.out.println(String.format(errorMessageTemplate, errorMessageArgs));
		}
	}

	private Token nextIdentifier() {

		StringBuffer buffer = new StringBuffer();

		char c = getChar();

		checkState(Character.isLetter(c), "Character %s is not a letter", c);

		while (Character.isLetter(c) || Character.isDigit(c) || c == '_' || c == '-') {
			buffer.append(c);
			c = getChar();
		}

		unputChar();
		return new Token(Type.IDENTIFIER, buffer.toString(), currentLineNumber);

	}

	private Token nextString() {

		StringBuffer buffer = new StringBuffer();

		char c = '\0';

		while (hasNextChar() && (c = getChar()) != '"') {
			buffer.append(c);
		}

		checkState(c == '"', "Character %s is not a \"", c);

		return new Token(Type.STRING, buffer.toString(), currentLineNumber);

	}

}
