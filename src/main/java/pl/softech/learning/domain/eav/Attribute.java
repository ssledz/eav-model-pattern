package pl.softech.learning.domain.eav;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import pl.softech.learning.domain.AbstractEntity;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity
public class Attribute extends AbstractEntity {

	public enum DataType {
		TEXT, DOUBLE, INTEGER, DICTIONARY, BOOLEAN, DATE
	}

	@Embedded
	@AttributeOverride(name = AttributeIdentifier.IDENTIFIER_PROPERTY, column = @Column(nullable = false, unique = true))
	private AttributeIdentifier identifier;

	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

	private String name;

	@Enumerated(EnumType.STRING)
	private DataType dataType;

	protected Attribute() {
	}
	
	public DataType getDataType() {
		return dataType;
	}

	public Attribute(AttributeIdentifier identifier, String name, Category category, DataType dataType) {
		super();
		this.identifier = checkNotNull(identifier);
		this.name = checkNotNull(name);
		this.category = checkNotNull(category);
		this.dataType = checkNotNull(dataType);
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
