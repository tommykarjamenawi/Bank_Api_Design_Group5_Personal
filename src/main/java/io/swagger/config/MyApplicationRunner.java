package io.swagger.config;

import io.swagger.model.Account;
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

import java.time.LocalDate;
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
        // Create 1 Employee and 1 Customer
        User firstUser = userService.createUser("biniam12", "biniam mehari", "secret", 1);
        User user2 = userService.createUser("tommy12", "tommy king", "secret", 0);
        // create a list of user
        List<User> users = new ArrayList<>(Arrays.asList(firstUser, user2));
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
        String str1 = "2022-04-03";
        String str2 = "2022-05-27";
        String str3 = "2022-05-27";
        String str4 = "2024-05-27";
        String str5 = "2024-05-27";
        String str6 = "2022-05-26";
        String str7 = "2022-05-26";
        String str8 = "2023-05-27";
        String str9 = "2023-05-27";
        LocalDate dateTime1 =  LocalDate.parse(str1);
        LocalDate dateTime2 = LocalDate.parse(str2);
        LocalDate dateTime3 = LocalDate.parse(str3);
        LocalDate dateTime4 =  LocalDate.parse(str4);
        LocalDate dateTime5 = LocalDate.parse(str5);
        LocalDate dateTime6 = LocalDate.parse(str6);
        LocalDate dateTime7 =  LocalDate.parse(str7);
        LocalDate dateTime8 = LocalDate.parse(str8);
        LocalDate dateTime9 = LocalDate.parse(str9);

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