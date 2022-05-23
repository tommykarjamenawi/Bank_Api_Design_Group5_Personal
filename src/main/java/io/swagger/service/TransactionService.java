package io.swagger.service;

import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> getAllTransactions(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getAllTransactionsBetween(startDate, endDate);
    }

    public Iterable<Transaction> getAllTransactionsByIBAN(String senderIban) {
        return transactionRepository.getTransactionByFromAccount(senderIban);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
