package pl.softech.learning.domain.eav;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import pl.softech.learning.domain.AbstractEntity;

@Entity
public class DomainObject extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

	private String name;

}
