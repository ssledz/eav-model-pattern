package pl.softech.eav.domain.dictionary;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author ssledz
 * @since 1.0
 */
public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {

	Dictionary findByIdentifier(@Param("identifier") DictionaryIdentifier identifier);
}
