package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.Account;
import org.springframework.validation.annotation.Validated;
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")

public class AccountResponseDTO {
    @JsonProperty("IBAN")
    private String IBAN = null;

    @JsonProperty("accountType")
    private String accountType = null;


    public AccountResponseDTO userId(Account account) {
        this.IBAN = account.getIBAN();
        this.accountType = account.getAccountType();
        return this;
    }
}
