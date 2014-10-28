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

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
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

		private List<Context> list = Lists.newLinkedList();

		public Builder withNamePropertyContext(NamePropertyContext namePropertyContext) {
			this.namePropertyContext = namePropertyContext;
			return this;
		}

		public void add(AttributeValueContext ctx) {
			list.add(ctx);
		}
		
		public void add(RelationSectionContext ctx) {
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
