package pl.softech.learning.domain.eav.dsl;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.Lists;

/**
 * @author ssledz
 */
public class RelationSectionContext implements Context {

	private final Context[] childrens;

	public RelationSectionContext(Builder builder) {
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

	static class Builder {

		private List<RelationValueContext> list = Lists.newLinkedList();

		public void addRelation(RelationValueContext ctx) {
			list.add(ctx);
		}

		private Context[] getContexts() {
			return list.toArray(new Context[list.size()]);
		}

	}

}
