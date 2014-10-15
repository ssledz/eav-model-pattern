package pl.softech.eav.domain.dictionary;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DictionaryRepository extends CrudRepository<Dictionary, Long> {

	Dictionary findByIdentifier(@Param("identifier") DictionaryIdentifier identifier);
}
