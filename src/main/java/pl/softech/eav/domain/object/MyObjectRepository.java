package pl.softech.eav.domain.object;

import org.springframework.data.repository.CrudRepository;

public interface MyObjectRepository extends CrudRepository<MyObject, Long> {

	MyObject findByName(String name);
	
}
