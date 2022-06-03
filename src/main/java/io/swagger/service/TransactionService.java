package io.swagger.service;

import io.swagger.model.*;
import io.swagger.model.dto.TransactionDTO;
import io.swagger.model.dto.TransactionResponseDTO;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.LocalDate;
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

    public List<Transaction> getAllTransactions(String startdate,String enddate) {

        LocalDate startDate;
        LocalDate endDate;
        try{
            // convert startDate and endDate to LocalDateTime object
            startDate = LocalDate.parse(startdate);
            endDate = LocalDate.parse(enddate);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "date needs to be in yyyy-MM-dd");
        }

        return transactionRepository.findAllByTimestampBetween(startDate, endDate);
    }


    public Transaction createTransaction(String username, TransactionDTO body) throws Exception {

        User user = userRepository.findByUsername(username);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.valueOf(body.getTransactionType().toLowerCase()));
        transaction.setFromAccount(body.getFromAccount());
        transaction.setToAccount(body.getToAccount());
        transaction.setAmount(body.getAmount());
        transaction.setTimestamp(LocalDate.now());
        transaction.setUserPerforming(user);

        return transactionRepository.save(transaction);
    }

    public List<TransactionResponseDTO> findAllTransactionsByIBANAccount(String iban, String datefrom, String dateto) {

        List<Transaction> transactions = new ArrayList<>();
        List<TransactionResponseDTO> transactionResponseList = new ArrayList<>();
        Account account = accountRepository.findByIBAN(iban);
        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(datefrom);
            endDate = LocalDate.parse(dateto);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid date format, needs to be in yyyy-MM-dd");
        }
        transactions.addAll(transactionRepository.getTransactionByFromAccountAndTimestampBetween(iban, startDate, endDate));
        transactions.addAll(transactionRepository.getTransactionByToAccountAndTimestampBetween(iban, startDate, endDate));


        for (Transaction transaction: transactions) {
            TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
            transactionResponseDTO.setTransactionId(transaction.getTransactionId());
            transactionResponseDTO.setUserPerforming(transaction.getUserPerforming().getUserId());
            transactionResponseDTO.setFromAccount(transaction.getFromAccount());
            transactionResponseDTO.setToAccount(transaction.getToAccount());
            transactionResponseDTO.setAmount(transaction.getAmount());
            transactionResponseDTO.setTimestamp(transaction.getTimestamp());
            transactionResponseDTO.setTransactionType(transaction.getTransactionType().toString());
            transactionResponseDTO.setBalanceAfterTransfer(account.getCurrentBalance());
            transactionResponseList.add(transactionResponseDTO);
        }
        return transactionResponseList;
    }

    public TransactionResponseDTO getTransactionResponseDTO(Transaction storeTransaction, User user, Account fromAccount) {
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();

        transactionResponseDTO.setTransactionId(storeTransaction.getTransactionId());
        transactionResponseDTO.setUserPerforming(user.getUserId());
        transactionResponseDTO.setFromAccount(storeTransaction.getFromAccount());
        transactionResponseDTO.setToAccount(storeTransaction.getToAccount());
        transactionResponseDTO.setAmount(storeTransaction.getAmount());
        transactionResponseDTO.setTransactionType(storeTransaction.getTransactionType().toString());
        transactionResponseDTO.setTimestamp(storeTransaction.getTimestamp());
        transactionResponseDTO.setBalanceAfterTransfer(fromAccount.getCurrentBalance());
        return transactionResponseDTO;
    }

    public List<Transaction> getAllTransactionsByAmount(String IBAN, Double amount, String operator) {
        List<Transaction> transactions = new ArrayList<>();

        switch(operator) {
            case "<":
                transactions.addAll(transactionRepository.findAllByAmountLessThanAndFromAccount(amount, IBAN));
                transactions.addAll(transactionRepository.findAllByAmountLessThanAndToAccount(amount, IBAN));
                return transactions;
            case ">":
                transactions.addAll(transactionRepository.findAllByAmountGreaterThanAndFromAccount(amount, IBAN));
                transactions.addAll(transactionRepository.findAllByAmountGreaterThanAndToAccount(amount, IBAN));
                return transactions;
            case "==":
                transactions.addAll(transactionRepository.findAllByAmountEqualsAndFromAccount(amount, IBAN));
                transactions.addAll(transactionRepository.findAllByAmountEqualsAndToAccount(amount, IBAN));
                return transactions;
            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "invalid operator value");
        }
    }
}
