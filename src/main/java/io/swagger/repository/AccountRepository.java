package io.swagger.repository;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    Account findByIBAN(String iban);

    // find all accounts belonging to a user
    List<Account> findAllByUser(User user);

    List<Account> findAllByUserAndAccountType(User user,String accountType);

   // List<Account> findAllByAccountIdAfterAndAccountIdBefore(Integer skip,Integer limit);
    List<Account> findAll();

    // get sum of all accounts currentBalance where account contains user
    @Query("select sum(account.currentBalance) from Account account where account.user =:user")
    Double getSumOfAllAccounts(User user);

  
}
