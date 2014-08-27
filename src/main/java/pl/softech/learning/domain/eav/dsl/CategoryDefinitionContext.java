package pl.softech.learning.domain.eav.dsl;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author ssledz 
 */
public class CategoryDefinitionContext implements Context {

	private final String identifier;

	private final Context[] childrens;

	public CategoryDefinitionContext(Builder builder) {
		this.identifier = builder.identifier;
		this.childrens = builder.getContexts();
	}

	public String getIdentifier() {
		return identifier;
	}
	
	@Override
	public void accept(ContextVisitor visitor) {
		visitor.visitOnEnter(this);

		for (Context ctx : childrens) {
			ctx.accept(visitor);
		}

		visitor.visitOnLeave(this);
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("identifier", identifier);
		sb.append("childrens", childrens);
		return sb.toString();
	}
	
	static class Builder implements NamePropertyContextAware<Builder> {

		private String identifier;

		private NamePropertyContext namePropertyContext;

		public Builder withIdentifier(String identifier) {
			this.identifier = identifier;
			return this;
		}

		@Override
		public Builder withNamePropertyContext(NamePropertyContext namePropertyContext) {
			this.namePropertyContext = namePropertyContext;
			return this;
		}

		private Context[] getContexts() {
			return new Context[] { namePropertyContext };
		}

	}

}
