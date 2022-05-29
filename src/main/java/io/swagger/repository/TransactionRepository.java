package io.swagger.repository;

import io.swagger.model.Transaction;
import io.swagger.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    // get all transactions with toAccount between two dates
    List<Transaction> getTransactionByToAccountAndTimestampBetween(String toAccount, LocalDate dateFrom, LocalDate dateTo);

    // get all transactions with fromAccount between two dates
    List<Transaction> getTransactionByFromAccountAndTimestampBetween(String fromAccount, LocalDate dateFrom, LocalDate dateTo);

    //return all transactions between from date and to date
    List<Transaction> findAllByTimestampBetween(LocalDate fromDate,LocalDate toDate);
}
