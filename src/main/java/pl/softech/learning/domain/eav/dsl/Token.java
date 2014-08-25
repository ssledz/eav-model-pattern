package pl.softech.learning.domain.eav.dsl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Token {

	enum Type {
		CATEGORY, ATTRIBUTE, OBJECT, OF, STRING, IDENTIFIER, COLON, END, EOF, NAME, DATA_TYPE;
	}

	private final Type type;
	private final String value;

	Token(Type type, String value) {
		this.type = type;
		this.value = value;
	}

	Token(Type type) {
		this(type, null);
	}

	public Type getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("type", type);
		sb.append("value", value);
		return sb.toString();
	}

}
