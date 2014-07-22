package pl.softech.learning.domain.eav;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByLastname(String lastname);
}
