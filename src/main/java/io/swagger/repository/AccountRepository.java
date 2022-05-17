package io.swagger.repository;

import io.swagger.model.Account;
import io.swagger.model.User;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer>{

}
