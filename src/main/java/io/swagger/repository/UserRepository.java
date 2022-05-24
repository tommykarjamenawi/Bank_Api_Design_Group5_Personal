package io.swagger.repository;

import io.swagger.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,Integer>{
    @Query("SELECT email FROM User WHERE email =:emailAddress")
    User findByEmailAddress(@Param("emailAddress") String emailAddress);

}
