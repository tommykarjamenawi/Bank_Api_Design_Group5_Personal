package io.swagger.repository;

import io.swagger.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer>{
    User findByUsername(String username);

    //List<User> findAllByIdGreaterThanEqualAndIdLessThanEqual(Integer skip, int i);
}
