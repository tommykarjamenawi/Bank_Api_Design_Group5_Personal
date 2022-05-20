package io.swagger.repository;

import io.swagger.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,Integer>{

    @Query("select user from User user where user.email =:email")
    User findByEmail(@Param("email") String email);
}
