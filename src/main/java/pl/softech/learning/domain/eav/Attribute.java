package pl.softech.learning.domain.eav;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import pl.softech.learning.domain.AbstractEntity;

@Entity
public class Attribute extends AbstractEntity {

	public enum DataType {
		STRING, DOUBLE, INTEGER, DICTIONARY, BOOLEAN
	}

	@Embedded
	@AttributeOverride(name = AttributeIdentifier.IDENTIFIER_PROPERTY, column = @Column(nullable = false, unique = true))
	private AttributeIdentifier identifier;

	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

	private String name;

	@Enumerated(EnumType.STRING)
	private DataType dataType;
}
