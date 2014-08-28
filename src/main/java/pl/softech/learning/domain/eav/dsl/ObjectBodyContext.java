package pl.softech.learning.domain.eav.dsl;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * @author ssledz
 */
public class ObjectBodyContext implements Context {

	private final Context[] childrens;

	public ObjectBodyContext(Builder builder) {
		this.childrens = builder.getContexts();
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
		sb.append("childrens", childrens);
		return sb.toString();
	}

	static class Builder implements NamePropertyContextAware<Builder> {

		private NamePropertyContext namePropertyContext;;

		private List<AttributeValueContext> list = Lists.newLinkedList();

		public Builder withNamePropertyContext(NamePropertyContext namePropertyContext) {
			this.namePropertyContext = namePropertyContext;
			return this;
		}

		public void add(AttributeValueContext ctx) {
			list.add(ctx);
		}

		private Context[] getContexts() {
			return new ImmutableList.Builder<Context>()//
					.addAll(list)//
					.add(namePropertyContext)//
					.build()//
					.toArray(new Context[0]);
		}

	}

}
