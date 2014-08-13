package pl.softech.learning.domain.eav;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import pl.softech.learning.domain.AbstractEntity;

@Entity
public class Category extends AbstractEntity {

	@Embedded
	@AttributeOverride(name = CategoryIdentifier.IDENTIFIER_PROPERTY, column = @Column(nullable = false, unique = true))
	private CategoryIdentifier identifier;

	private String name;
	
	protected Category() {
	}

	public Category(CategoryIdentifier identifier, String name) {
		this.identifier = checkNotNull(identifier);
		this.name = checkNotNull(name);
	}
	
	public CategoryIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.appendSuper(super.toString());
		sb.append("identifier", identifier);
		sb.append("name", name);
		return sb.toString();
	}

}
