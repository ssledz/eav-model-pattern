package pl.softech.eav.domain.dictionary;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author ssledz
 * @since 1.0
 */
public interface DictionaryEntryRepository extends CrudRepository<DictionaryEntry, Long> {

	DictionaryEntry findByIdentifier(@Param("identifier") DictionaryEntryIdentifier dictionaryEntryIdentifier);
}
