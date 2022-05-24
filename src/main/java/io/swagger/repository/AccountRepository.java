package io.swagger.repository;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    @Query("select account from Account account where account.user =:userId and account.accountType = :accountType")
    List<Account> checkCurrentAccount(User userId, String accountType);


    @Query("select account from Account account where account.IBAN =:iban")
    Account getByIBAN(String iban);


}
