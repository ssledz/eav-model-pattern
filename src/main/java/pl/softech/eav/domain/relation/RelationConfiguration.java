package pl.softech.eav.domain.relation;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import pl.softech.eav.domain.AbstractEntity;
import pl.softech.eav.domain.category.Category;
import pl.softech.eav.domain.category.CategoryIdentifier;

/**
 * @author ssledz
 */
@Entity
public class RelationConfiguration extends AbstractEntity {

	@Embedded
	@AttributeOverride(name = CategoryIdentifier.IDENTIFIER_PROPERTY, column = @Column(nullable = false, unique = true))
	private RelationIdentifier identifier;

	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	private Category owner;

	@ManyToOne(fetch = FetchType.LAZY)
	private Category target;

	protected RelationConfiguration() {
	}

	public RelationConfiguration(Builder builder) {
		this(new RelationIdentifier(builder.identifier), builder.name, builder.owner, builder.target);
	}

	public RelationConfiguration(RelationIdentifier identifier, String name, Category owner, Category target) {
		this.identifier = checkNotNull(identifier);
		this.name = checkNotNull(name);
		this.owner = checkNotNull(owner);
		this.target = checkNotNull(target);
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
