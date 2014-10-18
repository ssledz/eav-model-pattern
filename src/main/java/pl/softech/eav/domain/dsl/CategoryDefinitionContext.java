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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
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
