package pl.softech.eav.domain.attribute;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author ssledz
 * @since 1.0
 */
public interface AttributeRepository extends CrudRepository<Attribute, Long> {

	Attribute findByIdentifier(@Param("identifier") AttributeIdentifier identifier);
	
}
