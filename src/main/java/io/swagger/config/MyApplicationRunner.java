package io.swagger.config;

import io.swagger.model.Account;
import io.swagger.model.Role;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private MyWebSecurityConfig securityConfig;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        User firstUser = new User();
        firstUser.setUsername("biniam12");
        firstUser.setFullname("biniam mehari");
        firstUser.setPassword(securityConfig.passwordEncoder().encode("secret"));
        firstUser.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER, Role.ROLE_ADMIN)));

        User user2 = new User();
        user2.setUsername("tommy12");
        user2.setFullname("tommy king");
        user2.setPassword(securityConfig.passwordEncoder().encode("secret"));
        user2.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER)));
        // create a list of user
        List<User> users = new ArrayList<>(Arrays.asList(firstUser, user2));
        //List<User> users = List.of(firstUser);
        userRepository.saveAll(users);
        // create 100 dummy users for testing
        userService.create50RandomUsers();

        List<Account> accounts = List.of(
            new Account("NL01INHO0000000001",firstUser, Double.MAX_VALUE, "bank"),
                new Account("NL21INHO0123400081",user2, 50.00, "saving"),
                new Account("NL21INHO0123400001",user2, 75.00, "current")
        );

        accountRepository.saveAll(accounts);

        // Integer userPerformingId, String fromAccount, String toAccount, Double amount, String transactionType, java.time.LocalDateTime timestamp, Double balanceAfterTransfer
        String str1 = "2022-04-03T13:00:00";
        String str2 = "2022-05-27T13:00:00";
        String str3 = "2022-05-27T13:00:00";
        String str4 = "2024-05-27T13:00:00";
        String str5 = "2024-05-27T13:00:00";
        String str6 = "2022-05-26T13:00:00";
        String str7 = "2022-05-26T13:00:00";
        String str8 = "2023-05-27T13:00:00";
        String str9 = "2023-05-27T13:00:00";
        LocalDateTime dateTime1 =  LocalDateTime.parse(str1);
        LocalDateTime dateTime2 = LocalDateTime.parse(str2);
        LocalDateTime dateTime3 = LocalDateTime.parse(str3);
        LocalDateTime dateTime4 =  LocalDateTime.parse(str4);
        LocalDateTime dateTime5 = LocalDateTime.parse(str5);
        LocalDateTime dateTime6 = LocalDateTime.parse(str6);
        LocalDateTime dateTime7 =  LocalDateTime.parse(str7);
        LocalDateTime dateTime8 = LocalDateTime.parse(str8);
        LocalDateTime dateTime9 = LocalDateTime.parse(str9);


        List<Transaction> transactions = List.of(
                new Transaction(firstUser, "NL01INHO0000000001", "NL21INHO0123400001", 1900.00, "bank type", dateTime1, 500.00),
                new Transaction(firstUser, "NL01INHO0000000001", "NL21INHO0123400001", 2100.00, "bank type", dateTime2, 500.00),
                new Transaction(firstUser, "NL01INHO0000000001", "NL21INHO0123400001", 800.00, "bank type", dateTime1, 500.00),
                new Transaction(firstUser, "NL01INHO0000000001", "NL21INHO0123400001", 1200.00, "bank type", dateTime2, 500.00),
                new Transaction(firstUser, "NL01INHO0000000001", "NL21INHO0123400001", 700.00, "bank type", dateTime1, 500.00),
                new Transaction(firstUser, "NL01INHO0000000001", "NL21INHO0123400001", 500.00, "bank type", dateTime2, 500.00),
                new Transaction(user2, "NL21INHO0123400001", "NL01INHO0000000001", 1900.00, "bank type", dateTime7, 500.00),
                new Transaction(user2, "NL21INHO0123400001", "NL01INHO0000000001", 1000.00, "bank type", dateTime8, 500.00),
                new Transaction(user2, "NL21INHO0123400001", "NL01INHO0000000001", 2000.00, "bank type", dateTime9, 500.00),
                new Transaction(user2, "NL21INHO0123400001", "NL01INHO0000000001", 900.00, "bank type", dateTime9, 500.00)
        );
        transactionRepository.saveAll(transactions);
    }
}
