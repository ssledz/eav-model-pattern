package pl.softech.learning.domain.eav.dsl;

import pl.softech.learning.domain.eav.dsl.Token.Type;

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
