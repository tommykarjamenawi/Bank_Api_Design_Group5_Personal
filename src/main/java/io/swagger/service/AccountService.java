package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }

    public void deleteAccount(Account account) {
       accountRepository.delete(account);
    }

    public Account findByIBAN(String iban) {
        //New added
        Account account = accountRepository.findByIBAN(iban);
        //check if we return object of account
        if(account==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account is not find check the iban number");
        return account;
    }

    public List<Account> getAllAccountsOfUser(User user) {
        //new added to check the list is empty
        List<Account> accountsOfAUser = accountRepository.findAllByUser(user);
        if(accountsOfAUser==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"We can't find any account for this user");
        return accountsOfAUser;
    }

    public List<Account> findAllAccount(){
        //new added to check the list is empty
        List<Account> accounts = accountRepository.findAll();
        if(accounts==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"something going wrong when getting all accounts");
        return  accounts;
    }

    public List<Account> findAllByUserAndAccountType(User user,String accountType) {
        //New added
        List<Account> accounts = accountRepository.findAllByUserAndAccountType(user,accountType);
        //check if we return object of account
        if(accounts==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account is not find check the iban number");
        return accounts;
    }

}