package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Log
public class Transaction   {
  @Id
  @SequenceGenerator(name = "transaction_seq", initialValue = 1)
  @GeneratedValue(generator = "transaction_seq", strategy = GenerationType.SEQUENCE)
  private Integer transactionId;
  private Integer userPerformingId;
  private String fromAccount;
  private String toAccount;
  private Double amount ;
  private String transactionType;
  private java.time.LocalDateTime timestamp;
  private LocalDateTime lastUpdated;

  private Double balanceAfterTransfer;


  public Transaction(Integer userPerformingId, String fromAccount, String toAccount, Double amount, String transactionType, java.time.LocalDateTime timestamp, Double balanceAfterTransfer) {

    this.userPerformingId = userPerformingId;
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
    this.transactionType = transactionType;
    this.timestamp = timestamp;
    this.balanceAfterTransfer = balanceAfterTransfer;
  }
}
