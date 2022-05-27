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

    public List<Transaction> getAllTransactions(String username,
                                                String startDate,
                                                String endDate,
                                                Integer fromIndex, Integer limit) {
        // user id, two ibans filter all transaction for both dates
        User user = userRepository.findByUsername(username);
        // get all iban of user
        List<Account> accounts = accountRepository.findAllByUser(user);
        List<String> ibanList = new ArrayList<>();

        for (Account account : accounts) {
            ibanList.add(account.getIBAN());
        }

        List<Transaction> transactions = new ArrayList<>();

        // convert startDate and endDate to LocalDateTime object
        LocalDateTime startDateTime = LocalDateTime.parse(startDate);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate);


        //List<Account> accounts = accountRepository.findAllByUser(user);
        for (Role role : user.getRoles()) {

            if (role == Role.ROLE_USER) {
                for (String iban : ibanList) {
                    transactions.addAll(transactionRepository.getTransactionByFromAccountAndTimestampBetween(iban, startDateTime, endDateTime));
                }

                for (Account account : accounts) {
                    transactions.addAll(transactionRepository.getTransactionByToAccountAndTimestampBetween(account.getIBAN(), startDateTime, endDateTime));
                }
            }

            if (role == Role.ROLE_ADMIN) {
                transactions = (List<Transaction>) transactionRepository.findAll();
            }
        }

        if (transactions.size() <= 0) {
            return transactions;
        }
        return getTransactionsLimit(fromIndex, limit, transactions);
    }

    public List<Transaction> getTransactionsLimit(Integer fromIndex, Integer maxIndex, List<Transaction> transactions) {

        if (fromIndex < 0 || fromIndex > maxIndex || maxIndex <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "limit or offset cannot be less than zero, and offset cannot be grater than limit");
        }

        if (fromIndex == null) {
            fromIndex = 0;
        }

        if (maxIndex == null) {
            maxIndex = 10;
        }

        if (fromIndex == null && maxIndex == null) {
            return transactions;
        }

        return transactions.subList(fromIndex, maxIndex);
    }


    public Transaction createTransaction(String username, TransactionDTO transactionDTO) throws Exception {

       /* Account accountFrom = accountRepository.getByIBAN(transactionDTO.getFromAccount());
        Account accountTo = accountRepository.getByIBAN(transactionDTO.getToAccount());

        if (accountFrom.getAccountType().equals("saving") ||
                accountTo.getAccountType().equals("saving")) {
            if (accountFrom.getUser() != accountTo.getUser()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "saving and current account needs to be owned by one user only");
            }
        }*/

        if (transactionDTO.getFromAccount() == transactionDTO.getToAccount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "IBAN numbers of from and to accounts cannot be the same");
        }

        Transaction transaction = new Transaction();
        User user = userRepository.findByUsername(username);
        List<Account> accounts = accountRepository.findAllByUser(user);
        //List<String> ibans = new ArrayList<>();
        transaction.setFromAccount("svdjbvksjdnv");
        for (Account account: accounts) {
            if (account.getIBAN().equals(transactionDTO.getFromAccount())) {
                transaction.setFromAccount(transactionDTO.getToAccount());
                break;
            }
        }

        if (transaction.getFromAccount().equals("svdjbvksjdnv")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "you are not the owner of the account!");
        }
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found");
        }
        transaction.setUserPerforming(user);

        if (user.getTransactionLimit() <= 0.00 || user.getRemainingDayLimit() <= 0.00) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "transaction limit or userdaylimit cannot be null");
        }
        LocalDateTime transactionDate = LocalDateTime.now();

        switch (transactionDTO.getTransactionType()) {
            case "withdraw":
                transaction.setToAccount(transactionDTO.getToAccount());
                break;
            case "deposit":
                transaction.setToAccount("ATM deposit");
                break;
            case "bank transfer":
                transaction.setToAccount(transactionDTO.getToAccount());
                break;
        }
        transaction.setTimestamp(transactionDate);
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setAmount(transactionDTO.getAmount());
        Double balanceLeft = user.getTransactionLimit() - transactionDTO.getAmount();
        transaction.setBalanceAfterTransfer(balanceLeft);
        return transactionRepository.save(transaction);
    }


}
