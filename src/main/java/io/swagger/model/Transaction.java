package io.swagger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;
import org.threeten.bp.OffsetDateTime;

import javax.persistence.*;

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
  private OffsetDateTime timestamp;
  private Double balanceAfterTransfer;

  public Transaction(Integer userPerformingId, String fromAccount, String toAccount, Double amount, String transactionType, OffsetDateTime timestamp, Double balanceAfterTransfer) {
    this.transactionId = transactionId;
    this.userPerformingId = userPerformingId;
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
    this.transactionType = transactionType;
    this.timestamp = timestamp;
    this.balanceAfterTransfer = balanceAfterTransfer;
  }
}
