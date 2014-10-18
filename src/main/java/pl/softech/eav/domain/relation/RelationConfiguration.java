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
package pl.softech.eav.domain.relation;

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
import pl.softech.eav.domain.category.CategoryIdentifier;

/**
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
@Entity(name="pl.softech.eav.domain.relation.RelationConfiguration")
@Table(name = "eav_rel_configuration")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RelationConfiguration extends AbstractEntity {

	@Valid
	@Embedded
	@AttributeOverride(name = CategoryIdentifier.IDENTIFIER_PROPERTY, column = @Column(nullable = false, unique = true))
	private RelationIdentifier identifier;

	@TextMedium
	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_cat_id", nullable = false)
	private Category owner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "target_cat_id", nullable = false)
	private Category target;

	protected RelationConfiguration() {
	}

	public RelationConfiguration(Builder builder) {
		this(new RelationIdentifier(builder.identifier), builder.name, builder.owner, builder.target);
	}

	public RelationConfiguration(RelationIdentifier identifier, String name, Category owner, Category target) {
		this.identifier = checkNotNull(identifier, ARG_NOT_NULL_CHECK, "identifier");
		this.name = checkNotNull(name, ARG_NOT_NULL_CHECK, "name");
		this.owner = checkNotNull(owner, ARG_NOT_NULL_CHECK, "owner");
		this.target = checkNotNull(target, ARG_NOT_NULL_CHECK, "target");
	}

	public Category getOwner() {
		return owner;
	}

	public Category getTarget() {
		return target;
	}

	public String getName() {
		return name;
	}

	public RelationIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.appendSuper(super.toString());
		sb.append(identifier);
		sb.append("name", name);
		sb.append("owner", owner);
		sb.append("target", target);
		return sb.toString();
	}

	public static class Builder {

		private String identifier;

		private String name;

		private Category owner;

		private Category target;

		public Builder withIdentifier(String identifier) {
			this.identifier = identifier;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withOwner(Category owner) {
			this.owner = owner;
			return this;
		}

		public Builder withTarget(Category target) {
			this.target = target;
			return this;
		}

	}

}
