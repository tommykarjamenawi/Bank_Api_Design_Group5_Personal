package io.swagger.config;

import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public MyApplicationRunner(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{

        List<User> users = List.of(new User("Biniam","Tommy@","customer",1000.00,500.00,0.00));

        // Integer userPerformingId, String fromAccount, String toAccount, Double amount, String transactionType, java.time.LocalDateTime timestamp, Double balanceAfterTransfer
        String str = "2018-12-10 12:30";
        String str2 = "2018-12-11 13:30";
        String str3 = "2018-12-12 14:30";
        String str4 = "2018-12-13 15:30";
        String str5 = "2018-12-14 16:30";
        String str6 = "2018-12-15 17:30";
        String str7 = "2018-12-16 18:30";
        String str8 = "2021-05-15 12:45";
        String str9 = "2021-11-24 14:45";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime =  LocalDateTime.parse(str, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(str2, formatter);
        LocalDateTime dateTime3 = LocalDateTime.parse(str3, formatter);
        LocalDateTime dateTime4 =  LocalDateTime.parse(str4, formatter);
        LocalDateTime dateTime5 = LocalDateTime.parse(str5, formatter);
        LocalDateTime dateTime6 = LocalDateTime.parse(str6, formatter);
        LocalDateTime dateTime7 =  LocalDateTime.parse(str7, formatter);
        LocalDateTime dateTime8 = LocalDateTime.parse(str8, formatter);
        LocalDateTime dateTime9 = LocalDateTime.parse(str9, formatter);


        List<Transaction> transactions = List.of(
            new Transaction(1, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", dateTime, 500.00),
            new Transaction(2, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", dateTime2, 500.00),
                new Transaction(1, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", dateTime3, 500.00),
                new Transaction(2, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", dateTime4, 500.00),
                new Transaction(1, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", dateTime5, 500.00),
                new Transaction(2, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", dateTime6, 500.00),
                new Transaction(1, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", dateTime8, 500.00),
                new Transaction(1, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", dateTime8, 500.00),
                new Transaction(1, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", dateTime8, 500.00),
                new Transaction(1, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", dateTime9, 500.00)
        );
        userRepository.saveAll(users);
        transactionRepository.saveAll(transactions);
    }



}
