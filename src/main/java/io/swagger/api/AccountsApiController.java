package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.model.dto.AccountDTO;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.dto.AccountResponseDTO;
import io.swagger.service.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")
@RestController
@Api(tags = {"employee", "customer", "transaction"})

public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AccountService accountService;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> accountsIBANDelete(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "IBAN of a user", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Account> accountsIBANGet(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "IBAN of a user", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"dayLimit\" : 1000,\n  \"IBAN\" : \"NL14INHO1234567890\",\n  \"absoluteLimit\" : 0,\n  \"currentBalance\" : 100,\n  \"accountType\" : \"current\",\n  \"userId\" : 13\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Transaction>> accountsIBANTransactionsGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required=true, schema=@Schema()) @PathVariable("IBAN") Integer IBAN,@NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction from start date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "startDate", required = true) String startDate,@NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction till end date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "endDate", required = true) String endDate,@NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction from start date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "minValue", required = true) Integer minValue,@NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction till end date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "maxValue", required = true) Integer maxValue) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Transaction>>(objectMapper.readValue("[ {\n  \"transactionType\" : \"bank transfer\",\n  \"toAccount\" : \"NL45INHO9375867856\",\n  \"amount\" : 220.25,\n  \"userPerformingId\" : 50,\n  \"fromAccount\" : \"NL14INHO1234567890\",\n  \"balanceAfterTransfer\" : 100,\n  \"transactionId\" : 13,\n  \"timestamp\" : \"2022-07-21T17:32:28Z\"\n}, {\n  \"transactionType\" : \"bank transfer\",\n  \"toAccount\" : \"NL45INHO9375867856\",\n  \"amount\" : 220.25,\n  \"userPerformingId\" : 50,\n  \"fromAccount\" : \"NL14INHO1234567890\",\n  \"balanceAfterTransfer\" : 100,\n  \"transactionId\" : 13,\n  \"timestamp\" : \"2022-07-21T17:32:28Z\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<AccountResponseDTO> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "New account details", schema=@Schema()) @Valid @RequestBody AccountDTO body) {

        ModelMapper modelMapper = new ModelMapper();
        //Account account = modelMapper.map(body, Account.class)

        Account account = new Account();
        account.setIBAN(account.generateIBAN());
        account.setUser(new User());
        account.setDayLimit(500.00);
        account.setAbsoluteLimit(0.00);
        account.setCurrentBalance(0.00);
        account.setAccountType(body.getAccountType());

        account = accountService.createAccount(account);

        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setIBAN(account.getIBAN());
        accountResponseDTO.setAccountType(account.getAccountType());

        return new ResponseEntity<AccountResponseDTO>(accountResponseDTO,HttpStatus.CREATED);
    }

    public ResponseEntity<List<Account>> getAccounts(@NotNull @Parameter(in = ParameterIn.QUERY, description = "skips the list of users" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "skip", required = true) Integer skip,@NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch the needed amount of users" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "limit", required = true) Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Account>>(objectMapper.readValue("[ {\n  \"dayLimit\" : 1000,\n  \"IBAN\" : \"NL14INHO1234567890\",\n  \"absoluteLimit\" : 0,\n  \"currentBalance\" : 100,\n  \"accountType\" : \"current\",\n  \"userId\" : 13\n}, {\n  \"dayLimit\" : 1000,\n  \"IBAN\" : \"NL14INHO1234567890\",\n  \"absoluteLimit\" : 0,\n  \"currentBalance\" : 100,\n  \"accountType\" : \"current\",\n  \"userId\" : 13\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Account>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
