package pl.softech.learning.domain.eav.relation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RelationConfigurationRepository extends CrudRepository<RelationConfiguration, Long> {

	RelationConfiguration findByIdentifier(@Param("identifier") RelationConfiguration identifier);
	
}
