package io.swagger.service;

import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> getAllTransactions(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getAllTransactionsBetween(startDate, endDate);
    }

    public Iterable<Transaction> getAllTransactionsByIBAN(String senderIban) {
        return transactionRepository.getTransactionBySenderIBANAccount(senderIban);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
