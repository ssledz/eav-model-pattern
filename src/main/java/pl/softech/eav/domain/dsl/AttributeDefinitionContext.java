package pl.softech.eav.domain.dsl;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * @author ssledz 
 */
public class AttributeDefinitionContext implements Context {

	private final String identifier;

	private final Context[] childrens;

	AttributeDefinitionContext(Builder builder) {
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

		private CategoryPropertyContext categoryPropertyContext;
		private DataTypePropertyContext dataTypePropertyContext;
		private NamePropertyContext namePropertyContext;

		public Builder withIdentifier(String identifier) {
			this.identifier = identifier;
			return this;
		}

		public Builder withCategoryPropertyContext(CategoryPropertyContext categoryPropertyContext) {
			this.categoryPropertyContext = categoryPropertyContext;
			return this;
		}

		public Builder withDataTypePropertyContext(DataTypePropertyContext dataTypePropertyContext) {
			this.dataTypePropertyContext = dataTypePropertyContext;
			return this;
		}

		@Override
		public Builder withNamePropertyContext(NamePropertyContext namePropertyContext) {
			this.namePropertyContext = namePropertyContext;
			return this;
		}

		private Context[] getContexts() {

			return Collections2.filter(Arrays.asList(categoryPropertyContext, dataTypePropertyContext, namePropertyContext),
					new Predicate<Context>() {
						@Override
						public boolean apply(Context input) {
							return input != null;
						}
					}).toArray(new Context[0]);

		}
	}

}
