package pl.softech.eav.domain.relation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author ssledz
 */
public interface RelationConfigurationRepository extends CrudRepository<RelationConfiguration, Long> {

	RelationConfiguration findByIdentifier(@Param("identifier") RelationIdentifier identifier);

}
