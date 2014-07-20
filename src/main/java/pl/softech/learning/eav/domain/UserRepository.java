package pl.softech.learning.eav.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByLastname(String lastname);
}
