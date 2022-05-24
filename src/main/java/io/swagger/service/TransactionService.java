package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.model.dto.TransactionDTO;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Iterable<Transaction> getAllTransactions(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getAllTransactionsBetween(startDate, endDate);
    }

    public Iterable<Transaction> getAllTransactionsByIBAN(String senderIban) {
        return transactionRepository.getTransactionByFromAccount(senderIban);
    }

    public Transaction createTransaction(String email, TransactionDTO transactionDTO) throws Exception {
        /*email = "Tommy@";
        User user = findByEmailAddress(email);*/

        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTime = today.format(formatter);
        LocalDateTime transactionDate;

        try {
            transactionDate = LocalDateTime.parse(dateTime, formatter);
        }
        catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("datetime format is invalid");
        }

        /*if (user.getDayLimit() <= 0 || user.getTransactionLimit() <= 0) {
            throw new IllegalArgumentException("cannot do transaction daylimit or transaction limit cannot be less than zero");
        }*/

        Transaction transaction = new Transaction();

        switch(transactionDTO.getTransactionType()) {

            case "withdraw":
                transaction.setFromAccount("ATM transfer");// maybe atm IBAN number here
                transaction.setToAccount(transactionDTO.getToAccount());
                break;
            case "deposit":
                transaction.setToAccount("ATM"); // maybe atm IBAN number here
                transaction.setFromAccount(transactionDTO.getFromAccount());
                break;
            case "bank transfer":
                transaction.setFromAccount(transactionDTO.getFromAccount());
                transaction.setToAccount(transactionDTO.getToAccount());
                break;
        }
        transaction.setTimestamp(transactionDate);
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setUserPerformingId(1/*user.getUserId()*/);
        //Double balanceLeft = user.getDayLimit() - transactionDTO.getAmount();

        transaction.setBalanceAfterTransfer(500.00);
        return transactionRepository.save(transaction);
    }

    public User findByEmailAddress(String email) throws Exception {
        User user = userRepository.findByEmailAddress(email);
        if (user == null) {
            throw new Exception("user cannot be null");
        }
        return user;
    }


}
