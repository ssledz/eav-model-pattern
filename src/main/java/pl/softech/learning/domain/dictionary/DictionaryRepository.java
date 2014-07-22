package pl.softech.learning.domain.dictionary;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface DictionaryRepository extends Repository<Dictionary, Long> {

	Dictionary findOne(Long entityId);

	Dictionary findByIdentifier(@Param("identifier") String identifier);
}
