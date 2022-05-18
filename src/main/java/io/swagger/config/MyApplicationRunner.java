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
        LocalDateTime startDate = LocalDateTime.of(2022, 02, 13, 17, 48, 33);
        List<User> users = List.of(new User("Biniam","Tommy@","customer",1000.00,500.00,0.00));
        LocalDateTime startDate1 = LocalDateTime.of(2022, 02, 13, 18, 15, 33);
        // Integer userPerformingId, String fromAccount, String toAccount, Double amount, String transactionType, java.time.LocalDateTime timestamp, Double balanceAfterTransfer
        List<Transaction> transactions = List.of(
            new Transaction(1, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", startDate, 500.00),
            new Transaction(1, "jjsdbjkbsdjbvkjsdn", "sjbdvkjsdvskdvn", 2000.00, "bank type", startDate1, 500.00)
        );
        userRepository.saveAll(users);
        transactionRepository.saveAll(transactions);
    }



}
