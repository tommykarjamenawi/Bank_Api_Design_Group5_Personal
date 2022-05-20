package io.swagger.config;

import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> users = List.of(new User("biniam mehari", "biniam@inholland.nl", "employee", 1000.00, 500.00, 0.00));
        userRepository.saveAll(users);

        // Create an account for the BANK at system startup
        List<Account> accounts = List.of(new Account("NL01INHO0000000001",new User(), Double.MAX_VALUE, "bank"));
        accountRepository.saveAll(accounts);
    }

}
