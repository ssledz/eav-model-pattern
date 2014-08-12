package pl.softech.learning.domain.eav;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AttributeRepository extends CrudRepository<Attribute, Long> {

	Attribute findByIdentifier(@Param("identifier") AttributeIdentifier identifier);
	
}
