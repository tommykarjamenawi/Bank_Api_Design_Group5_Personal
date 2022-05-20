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


    public boolean checkCurrentAccount(User userId) {
        List<Account> accounts = accountRepository.checkCurrentAccount(userId,"current");
        if(accounts.isEmpty()){
            return false;
        }
        return true;
    }

    public void deleteAccount(String iban) {
        Account accountToDelete = accountRepository.getByIBAN(iban);
        if(accountToDelete==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        accountRepository.delete(accountToDelete);
    }

    public Account getAccountByIBAN(String iban) {
        return accountRepository.getByIBAN(iban);
    }
}
