package pl.softech.learning.domain.eav.relation;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import pl.softech.learning.domain.AbstractEntity;
import pl.softech.learning.domain.eav.category.Category;
import pl.softech.learning.domain.eav.category.CategoryIdentifier;

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

}
