package io.swagger.config;

import io.swagger.model.Account;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User firstUser = new User();
        firstUser.setUsername("biniam");
        firstUser.setEmail("biniam@inholland.nl");
        firstUser.setPassword("secret");
        firstUser.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER, Role.ROLE_ADMIN)));

        User user2 = new User();
        user2.setUsername("tommy");
        user2.setEmail("tommy@inholland.nl");
        user2.setPassword("secret");
        user2.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER)));

        // create a list of user
        List<User> users = new ArrayList<>(Arrays.asList(firstUser, user2));
        //List<User> users = List.of(firstUser);
        userRepository.saveAll(users);

        // Create an account for the BANK at system startup
        List<Account> accounts = List.of(new Account("NL01INHO0000000001",new User(), Double.MAX_VALUE, "bank"));
        accountRepository.saveAll(accounts);
    }

}
