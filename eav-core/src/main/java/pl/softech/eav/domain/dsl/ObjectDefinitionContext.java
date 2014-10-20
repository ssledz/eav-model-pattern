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

import com.google.common.collect.Lists;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class ObjectDefinitionContext implements Context {

	private final String objectIdentifier;
	private final String categoryIdentifier;

	private final Context[] childrens;

	public ObjectDefinitionContext(Builder builder) {
		this.objectIdentifier = builder.objectIdentifier;
		this.categoryIdentifier = builder.categoryIdentifier;
		this.childrens = builder.getContexts();
	}

	public String getObjectIdentifier() {
		return objectIdentifier;
	}

	public String getCategoryIdentifier() {
		return categoryIdentifier;
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
		sb.append("objectIdentifier", objectIdentifier);
		sb.append("categoryIdentifier", categoryIdentifier);
		sb.append("childrens", childrens);
		return sb.toString();
	}

	static class Builder {

		private String objectIdentifier;
		private String categoryIdentifier;

		private List<ObjectBodyContext> contexts = Lists.newLinkedList();

		public Builder add(ObjectBodyContext ctx) {
			contexts.add(ctx);
			return this;
		}

		public Builder withObjectIdentifier(String objectIdentifier) {
			this.objectIdentifier = objectIdentifier;
			return this;
		}

		public Builder withCategoryIdentifier(String categoryIdentifier) {
			this.categoryIdentifier = categoryIdentifier;
			return this;
		}

		private Context[] getContexts() {
			return contexts.toArray(new Context[0]);
		}

	}

}
