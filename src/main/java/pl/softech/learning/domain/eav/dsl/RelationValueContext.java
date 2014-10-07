package pl.softech.learning.domain.eav.dsl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author ssledz
 */
public class RelationValueContext implements Context {

	private final String relationIdentifier;
	private final String value;

	public RelationValueContext(Builder builder) {
		this.relationIdentifier = builder.relationIdentifier;
		this.value = builder.value;
	}

	public String getRelationIdentifier() {
		return relationIdentifier;
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
		sb.append("relationIdentifier", relationIdentifier);
		sb.append("value", value);
		return sb.toString();
	}

	static class Builder {

		private String relationIdentifier;
		private String value;

		public Builder withRelationIdentifier(String relationIdentifier) {
			this.relationIdentifier = relationIdentifier;
			return this;
		}

		public Builder withValue(String value) {
			this.value = value;
			return this;
		}

	}

}
