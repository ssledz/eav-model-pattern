package pl.softech.learning.domain.dictionary;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DictionaryEntryRepository extends CrudRepository<DictionaryEntry, Long> {

	DictionaryEntry findByIdentifier(@Param("identifier") DictionaryEntryIdentifier dictionaryEntryIdentifier);
}
