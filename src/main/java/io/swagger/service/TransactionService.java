package io.swagger.service;

import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.model.dto.TransactionDTO;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        return transactionRepository.getTransactionBySenderIBANAccount(senderIban);
    }

    public Transaction createTransaction(String email, TransactionDTO transactionDTO) throws Exception {

        User user = findByEmailAddress(email);
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
        transaction.setUserPerformingId(user.getUserId());
        Double balanceLeft = user.getDayLimit() - transactionDTO.getAmount();

        transaction.setBalanceAfterTransfer(balanceLeft);
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
