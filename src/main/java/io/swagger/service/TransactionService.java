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

    public List<TransactionResponseDTO>  findAllTransactionsByIBANAccount(String iban, String datefrom, String dateto, User user) {

        List<Transaction> transactions = new ArrayList<>();
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
          List<TransactionResponseDTO> transactionResponseDTOS = new ArrayList<>();
        for (Transaction transaction: transactions) {
         TransactionResponseDTO tr = getTransactionResponseDTO(transaction);
         transactionResponseDTOS.add(tr);
        }
            return transactionResponseDTOS;
    }

    public TransactionResponseDTO getTransactionResponseDTO(Transaction storeTransaction) {
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();

        transactionResponseDTO.setTransactionId(storeTransaction.getTransactionId());
        transactionResponseDTO.setUserPerformingId(storeTransaction.getUserPerforming().getUserId());
        transactionResponseDTO.setFromAccount(storeTransaction.getFromAccount());
        transactionResponseDTO.setToAccount(storeTransaction.getToAccount());
        transactionResponseDTO.setAmount(storeTransaction.getAmount());
        transactionResponseDTO.setTransactionType(storeTransaction.getTransactionType().toString());
        transactionResponseDTO.setTimestamp(storeTransaction.getTimestamp());
        return transactionResponseDTO;
    }

    public List<TransactionResponseDTO> getAllTransactionsByAmount(String IBAN, Double amount, String operator) {
        List<Transaction> transactions = new ArrayList<>();
        List<TransactionResponseDTO> transactionResponseDTOs = new ArrayList<>();

        if (operator.equals("<")) {
            transactions.addAll(transactionRepository.findAllByAmountLessThanAndFromAccount(amount, IBAN));
            transactions.addAll(transactionRepository.findAllByAmountLessThanAndToAccount(amount, IBAN));
        }

        else if (operator.equals(">")) {
            transactions.addAll(transactionRepository.findAllByAmountGreaterThanAndFromAccount(amount, IBAN));
            transactions.addAll(transactionRepository.findAllByAmountGreaterThanAndToAccount(amount, IBAN));
        }

        else if (operator.equals("=")) {
            transactions.addAll(transactionRepository.findAllByAmountEqualsAndFromAccount(amount, IBAN));
            transactions.addAll(transactionRepository.findAllByAmountEqualsAndToAccount(amount, IBAN));
        }

        for (Transaction transaction: transactions) {
            TransactionResponseDTO transactionResponseDTO = getTransactionResponseDTO(transaction);
            transactionResponseDTOs.add(transactionResponseDTO);
        }
        // TODO:  sort the array by transactionId
        return transactionResponseDTOs;
    }



}
