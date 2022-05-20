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
import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Log
public class Account   {

  @OneToOne
  private User user;
  @Id
  private String IBAN;
  private Double dayLimit;
  private Double absoluteLimit;
  private Double currentBalance;
  private String accountType;
}
