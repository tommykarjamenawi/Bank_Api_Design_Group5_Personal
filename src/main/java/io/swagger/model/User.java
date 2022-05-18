package io.swagger.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.util.Objects;
import javax.persistence.*;


/**
 * User
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString // the tostring method
@Log // to see whats happening in the class
public class User   {
  @Id
  @SequenceGenerator(name = "users_seq", initialValue = 1)
  @GeneratedValue(generator = "users_seq", strategy = GenerationType.SEQUENCE)
  private Integer userId;
  private String fullName;
  private String email;
  private String role;
  private String password;
  private Double dayLimit = 5000.00;
  private Double transactionLimit= 1000.00;
  private Double remainingDayLimit=0.00;

  public User(String fullName, String email, String role, Double dayLimit, Double transactionLimit, Double remainingDayLimit) {
    this.fullName = fullName;
    this.email = email;
    this.role = role;
    this.dayLimit = dayLimit;
    this.transactionLimit = transactionLimit;
    this.remainingDayLimit = remainingDayLimit;
  }


}
