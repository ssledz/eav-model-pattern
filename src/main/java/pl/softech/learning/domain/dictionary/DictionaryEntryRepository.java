package pl.softech.learning.domain.dictionary;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface DictionaryEntryRepository extends Repository<DictionaryEntry, Long> {

    DictionaryEntry findOne(Long entityId);

    DictionaryEntry findByIdentifier(@Param("identifier") String dictionaryIdentifier,
	    @Param("entryIdentifier") String dictionaryEntryIdentifier);
}
