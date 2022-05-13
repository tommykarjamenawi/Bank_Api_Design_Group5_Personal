package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransactionDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T12:23:27.162Z[GMT]")


public class TransactionDTO   {
  @JsonProperty("fromAccount")
  private String fromAccount = null;

  @JsonProperty("toAccount")
  private String toAccount = null;

  @JsonProperty("amount")
  private Double amount = null;

  @JsonProperty("transactionType")
  private String transactionType = null;

  public TransactionDTO fromAccount(String fromAccount) {
    this.fromAccount = fromAccount;
    return this;
  }

  /**
   * Get fromAccount
   * @return fromAccount
   **/
  @Schema(example = "NL14INHO1234567890", description = "")
  
  @Size(min=18,max=18)   public String getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(String fromAccount) {
    this.fromAccount = fromAccount;
  }

  public TransactionDTO toAccount(String toAccount) {
    this.toAccount = toAccount;
    return this;
  }

  /**
   * Get toAccount
   * @return toAccount
   **/
  @Schema(example = "NL45INHO9375867856", description = "")
  
  @Size(min=18,max=18)   public String getToAccount() {
    return toAccount;
  }

  public void setToAccount(String toAccount) {
    this.toAccount = toAccount;
  }

  public TransactionDTO amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "220.25", description = "")
  
    public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public TransactionDTO transactionType(String transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  /**
   * Get transactionType
   * @return transactionType
   **/
  @Schema(example = "bank transfer", description = "")
  
    public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionDTO transactionDTO = (TransactionDTO) o;
    return Objects.equals(this.fromAccount, transactionDTO.fromAccount) &&
        Objects.equals(this.toAccount, transactionDTO.toAccount) &&
        Objects.equals(this.amount, transactionDTO.amount) &&
        Objects.equals(this.transactionType, transactionDTO.transactionType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromAccount, toAccount, amount, transactionType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionDTO {\n");
    
    sb.append("    fromAccount: ").append(toIndentedString(fromAccount)).append("\n");
    sb.append("    toAccount: ").append(toIndentedString(toAccount)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
