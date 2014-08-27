package pl.softech.learning.domain.eav.dsl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author ssledz
 */
public class AttributeValueContext implements Context {

	private final String attributeIdentifier;
	private final String value;

	public AttributeValueContext(Builder builder) {
		this.attributeIdentifier = builder.attributeIdentifier;
		this.value = builder.value;
	}

	public String getAttributeIdentifier() {
		return attributeIdentifier;
	}

	public String getValue() {
		return value;
	}

	@Override
	public void accept(ContextVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("attributeIdentifier", attributeIdentifier);
		sb.append("value", value);
		return sb.toString();
	}

	static class Builder {

		private String attributeIdentifier;
		private String value;

		public Builder withAttributeIdentifier(String attributeIdentifier) {
			this.attributeIdentifier = attributeIdentifier;
			return this;
		}

		public Builder withValue(String value) {
			this.value = value;
			return this;
		}

	}

}
