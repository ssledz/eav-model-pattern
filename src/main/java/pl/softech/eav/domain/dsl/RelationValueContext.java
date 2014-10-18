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
