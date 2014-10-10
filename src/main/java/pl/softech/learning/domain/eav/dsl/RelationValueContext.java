package pl.softech.learning.domain.eav.dsl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author ssledz
 */
public class RelationValueContext implements Context {

	private final String relationIdentifier;
	private final String objectIdentifier;

	public RelationValueContext(Builder builder) {
		this.relationIdentifier = builder.relationIdentifier;
		this.objectIdentifier = builder.objectIdentifier;
	}

	public String getRelationIdentifier() {
		return relationIdentifier;
	}

	public String getObjectIdentifier() {
		return objectIdentifier;
	}

	@Override
	public void accept(ContextVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("relationIdentifier", relationIdentifier);
		sb.append("objectIdentifier", objectIdentifier);
		return sb.toString();
	}

	static class Builder {

		private String relationIdentifier;
		private String objectIdentifier;

		public Builder withRelationIdentifier(String relationIdentifier) {
			this.relationIdentifier = relationIdentifier;
			return this;
		}

		public Builder withObjectIdentifier(String identifier) {
			this.objectIdentifier = identifier;
			return this;
		}

	}

}
