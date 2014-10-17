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
public class AttributeValueContext implements Context {

	private final String attributeIdentifier;
	private final String value;

	public AttributeValueContext(Builder builder) {
		this.attributeIdentifier = builder.attributeIdentifier;
		this.value = builder.value;
	}

	public String getAttributeIdentifier() {
		return attributeIdentifier;
	}

	public String getValue() {
		return value;
	}

	@Override
	public void accept(ContextVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("attributeIdentifier", attributeIdentifier);
		sb.append("value", value);
		return sb.toString();
	}

	static class Builder {

		private String attributeIdentifier;
		private String value;

		public Builder withAttributeIdentifier(String attributeIdentifier) {
			this.attributeIdentifier = attributeIdentifier;
			return this;
		}

		public Builder withValue(String value) {
			this.value = value;
			return this;
		}

	}

}
