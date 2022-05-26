package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.Role;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.model.dto.TransactionDTO;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> getAllTransactions(String username, LocalDateTime startDate, LocalDateTime endDate) {
        // user id, two ibans filter all transaction for both dates
        User user = userRepository.findByUsername(username);
        List<Transaction> transactions = new ArrayList<>();


        if (user == null || user.getUsername() != username) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password is incorrect");
        }
        List<Account> accounts = accountRepository.findAllByUserId(user.getUserId());
        for (Role role: user.getRoles()) {

            if (role == Role.ROLE_USER) {
                transactions = transactionRepository.
                        getTransactionByUserPerformingIdAndTimestampBetween(user.getUserId(),
                                startDate, endDate);

                for (Account account: accounts) {
                    transactions = transactionRepository.getTransactionByFromAccountAndTimestampBetween(account.getIBAN(), startDate, endDate);
                    transactions = transactionRepository.getTransactionByToAccountAndTimestampBetween(account.getIBAN(), startDate, endDate);

                }
            }

            if (role == Role.ROLE_ADMIN) {
                transactions = (List<Transaction>) transactionRepository.findAll();
            }
        }
        return transactions;
    }

    public Transaction createTransaction(String username, TransactionDTO transactionDTO) throws Exception {

        // check if IBAN numbers of both accounts are the same
        Transaction transaction = new Transaction();
        if (transaction.getFromAccount() == transaction.getToAccount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IBAN numbers of from and to accounts cannot be the same");
        }
        User user = userRepository.findByUsername(username);
        List<Account> accounts = findAccountById(user);

        if (accounts == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "accounts cannot be null");
        }

        if(user.getTransactionLimit() <= 0.00 || user.getRemainingDayLimit() <= 0.00) {
            throw new IllegalArgumentException("error! cannot create transaction your transaction limit and day limit is 0.00");
        }

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

        switch(transactionDTO.getTransactionType()) {

            case "withdraw":
                transaction.setFromAccount("ATM transfer");
                transaction.setToAccount(transactionDTO.getToAccount());
                break;
            case "deposit":
                transaction.setToAccount("ATM deposit");
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



    List<Account> findAccountById(User user) {
        if(user == null || accountRepository.findAllByUserId(user.getUserId()).size() <= 0) {
            return null;
        }
        return accountRepository.findAllByUserId(user.getUserId());
    }


}
