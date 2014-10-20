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
package pl.softech.eav.domain.attribute;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import pl.softech.eav.domain.AbstractEntity;
import pl.softech.eav.domain.TextMedium;
import pl.softech.eav.domain.category.Category;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
@Entity(name="pl.softech.eav.domain.attribute.Attribute")
@Table(name = "eav_attribute")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attribute extends AbstractEntity {

	@Valid
	@Embedded
	@AttributeOverride(name = AttributeIdentifier.IDENTIFIER_PROPERTY, column = @Column(nullable = false, unique = true))
	private AttributeIdentifier identifier;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@TextMedium
	@Column(nullable = false)
	private String name;

	@Embedded
	private DataType dataType;

	protected Attribute() {
	}

	public Attribute(Builder builder) {
		this(new AttributeIdentifier(builder.identifier), builder.name, builder.category, new DataType(builder.dataType));
	}

	public Attribute(AttributeIdentifier identifier, String name, Category category, DataType dataType) {
		this.identifier = checkNotNull(identifier, ARG_NOT_NULL_CHECK, "identifier");
		this.name = checkNotNull(name, ARG_NOT_NULL_CHECK, "name");
		this.category = checkNotNull(category, ARG_NOT_NULL_CHECK, "category");
		this.dataType = checkNotNull(dataType, ARG_NOT_NULL_CHECK, "dataType");
	}

	public Category getCategory() {
		return category;
	}

	public AttributeIdentifier getIdentifier() {
		return identifier;
	}

	public DataType getDataType() {
		return dataType;
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.appendSuper(super.toString());
		sb.append("identifier", identifier);
		sb.append("name", name);
		sb.append("category", category);
		sb.append("dataType", dataType);
		return sb.toString();
	}

	public static class Builder {

		private String identifier;

		private Category category;

		private String name;

		private DataType.Builder dataType;

		public Builder withIdentifier(String identifier) {
			this.identifier = identifier;
			return this;
		}

		public Builder withCategory(Category category) {
			this.category = category;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withDataType(DataType.Builder dataType) {
			this.dataType = dataType;
			return this;
		}

	}

}
