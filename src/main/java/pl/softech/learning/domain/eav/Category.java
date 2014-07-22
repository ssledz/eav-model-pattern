package pl.softech.learning.domain.eav;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import pl.softech.learning.domain.AbstractEntity;

@Entity
public class Category extends AbstractEntity {

	@Embedded
	@AttributeOverride(name = CategoryIdentifier.IDENTIFIER_PROPERTY, column = @Column(nullable = false, unique = true))
	private CategoryIdentifier identifier;

	private String name;

}
