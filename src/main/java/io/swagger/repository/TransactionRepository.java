package io.swagger.repository;

import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    Iterable<Transaction> getTransactionByTimestamp(LocalDateTime timestamp);

    @Query(value = "SELECT transactionId, userPerformingId, fromAccount, toAccount, amount, " +
            " transactionType, timestamp, balanceAfterTransfer FROM Transaction WHERE timestamp BETWEEN :startDate AND :endDate")
    Iterable<Transaction> getAllTransactionsBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    Iterable<Transaction> getTransactionByFromAccount(String senderIBANumber);
}
