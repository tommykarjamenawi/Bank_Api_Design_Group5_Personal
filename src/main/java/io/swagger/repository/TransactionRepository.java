package io.swagger.repository;

import io.swagger.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    List<Transaction> findBytimestampCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);
}
