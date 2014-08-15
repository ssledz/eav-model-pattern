package pl.softech.learning.domain.eav;

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

@Entity
public class Attribute extends AbstractEntity {

	@Embedded
	@AttributeOverride(name = AttributeIdentifier.IDENTIFIER_PROPERTY, column = @Column(nullable = false, unique = true))
	private AttributeIdentifier identifier;

	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

	private String name;

	@Embedded
	private DataType dataType;

	protected Attribute() {
	}

	public Attribute(AttributeIdentifier identifier, String name, Category category, DataType dataType) {
		super();
		this.identifier = checkNotNull(identifier);
		this.name = checkNotNull(name);
		this.category = checkNotNull(category);
		this.dataType = checkNotNull(dataType);
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

}
