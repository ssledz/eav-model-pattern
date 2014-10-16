package pl.softech.eav.domain.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author ssledz
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

	Category findByIdentifier(@Param("identifier") CategoryIdentifier identifier);
	
}
