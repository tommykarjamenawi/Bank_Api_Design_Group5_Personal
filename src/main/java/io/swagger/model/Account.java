package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.dto.AccountDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
public class Account{

  @Id
  @SequenceGenerator(name = "account_seq", initialValue = 1)
  @GeneratedValue(generator = "account_seq", strategy = GenerationType.SEQUENCE)
  private Integer accountId;
  private String IBAN;
  @ManyToOne(cascade = CascadeType.ALL)
  private User user;
  private Double dayLimit;
  private Double absoluteLimit;
  private Double currentBalance;
  private String accountType;


  // to create the bank account
  public Account(String IBAN, User user, Double dayLimit, Double absoluteLimit, Double currentBalance, String accountType) {
    this.IBAN = IBAN;
    this.user = user;
    this.dayLimit = dayLimit;
    this.absoluteLimit = absoluteLimit;
    this.currentBalance = currentBalance;
    this.accountType = accountType;
  }

  // generate the IBAN
  public String generateIBAN() {
    String countryCode = "NL";
    // create random number between 2 and 99
    int randomNumber = (int) (Math.random() * (99 - 2 + 1) + 2);
    String randomNumberString = String.valueOf(randomNumber);
    if (randomNumber < 10) {
      randomNumberString = "0" + randomNumberString;
    }
    String bankCode = "INHO";
     // create a 10 digit random number
    int randomNumber2 = (int) (Math.random() * (999999999 - 100000000 + 1) + 100000000);
    int lastDigit = (int) (Math.random() * (9 - 0 + 1) + 0);
    String randomNumberString2 = String.valueOf(randomNumber2);

    String iban = countryCode + randomNumberString + bankCode + randomNumberString2 + lastDigit;
    return iban;
  }

}
