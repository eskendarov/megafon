package ru.megafon.test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.megafon.test.domain.User;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends CrudRepository<User, Integer> {

}
