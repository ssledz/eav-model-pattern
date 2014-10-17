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
 * @author ssledz
 * @since 1.0
 */
public class Lexer {

	private int current;
	private String input;

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
			return new Token(Type.EOF);
		}

		char c = getChar();

		while (Character.isWhitespace(c)) {

			if (!hasNextChar()) {
				return new Token(Type.EOF);
			}

			c = getChar();
		}

		if (c == '#') {
			while (c != '\n') {
				c = getChar();
			}
			return next();
		}

		if (c == '"') {
			return nextString();
		}

		if (c == ':') {
			return new Token(Type.COLON);
		}

		unputChar();

		Token token = nextIdentifier();

		switch (token.getValue().toLowerCase()) {

		case "category":
			return new Token(Type.CATEGORY);
		case "attribute":
			return new Token(Type.ATTRIBUTE);
		case "object":
			return new Token(Type.OBJECT);
		case "name":
			return new Token(Type.NAME);
		case "data_type":
			return new Token(Type.DATA_TYPE);
		case "of":
			return new Token(Type.OF);
		case "end":
			return new Token(Type.END);
		case "dictionary":
			return new Token(Type.DICTIONARY);
		case "owner":
			return new Token(Type.OWNER);
		case "target":
			return new Token(Type.TARGET);
		case "relation":
			return new Token(Type.RELATION);
		case "relations":
			return new Token(Type.RELATIONS);
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

		while (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
			buffer.append(c);
			c = getChar();
		}

		unputChar();
		return new Token(Type.IDENTIFIER, buffer.toString());

	}

	private Token nextString() {

		StringBuffer buffer = new StringBuffer();

		char c = '\0';

		while (hasNextChar() && (c = getChar()) != '"') {
			buffer.append(c);
		}

		checkState(c == '"', "Character %s is not a \"", c);

		return new Token(Type.STRING, buffer.toString());

	}

}
