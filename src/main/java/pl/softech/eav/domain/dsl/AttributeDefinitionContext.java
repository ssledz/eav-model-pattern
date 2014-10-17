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

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * @author ssledz
 * @since 1.0
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
