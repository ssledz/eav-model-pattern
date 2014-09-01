package pl.softech.learning.domain.eav.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	Category findByIdentifier(@Param("identifier") CategoryIdentifier identifier);
	
}
