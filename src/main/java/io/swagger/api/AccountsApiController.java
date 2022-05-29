package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.Account;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.model.dto.AccountDTO;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.dto.AccountResponseDTO;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")
@RestController
@Api(tags = {"employee", "customer", "transaction"})

public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;


    @Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> accountsIBANDelete(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "IBAN of a user", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN) {
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String username = userAuthentication.getName();
        User user = userService.getUserByUsername(username);
        //receiving the account form database
        Account account = accountService.findByIBAN(IBAN);
        if(account==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account is not find check the iban number");

        if(user.getRoles().contains(Role.ROLE_ADMIN) || user.getAccounts().contains(account)){
            accountService.deleteAccount(account);
        }
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You dont have authorization to delete this account");



        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Account> accountsIBANGet(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "IBAN of a user", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN) {
        // getes the data of a user from the token
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String username = userAuthentication.getName();
        User user = userService.getUserByUsername(username);

        //receiving the account form database
        Account account = accountService.findByIBAN(IBAN);
        if(account==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account is not find check the iban number");

        //check if the user is owner of the account or admin(employee)
        if(user.getRoles().contains(Role.ROLE_ADMIN) || user.getAccounts().contains(account)){
            return new ResponseEntity<Account>(account, HttpStatus.OK);
        }
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You dont have authorization to get this account information");

    }

    public ResponseEntity<List<Transaction>> accountsIBANTransactionsGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required = true, schema = @Schema()) @PathVariable("IBAN") Integer IBAN, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction from start date", required = true, schema = @Schema()) @Valid @RequestParam(value = "startDate", required = true) String startDate, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction till end date", required = true, schema = @Schema()) @Valid @RequestParam(value = "endDate", required = true) String endDate, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction from start date", required = true, schema = @Schema()) @Valid @RequestParam(value = "minValue", required = true) Integer minValue, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction till end date", required = true, schema = @Schema()) @Valid @RequestParam(value = "maxValue", required = true) Integer maxValue) {
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

    public ResponseEntity<AccountResponseDTO> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "New account details", schema = @Schema()) @Valid @RequestBody AccountDTO body) {

        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String username = userAuthentication.getName();
        User user = userService.getUserByUsername(username);

        Account account = new Account();
        account.setIBAN(account.generateIBAN());
        account.setAccountType(body.getAccountType());
        account.setUser(userService.getUserModelById(body.getUserId()));


        // check if user is admin or user looged
        if(user.getUserId().equals(body.getUserId())){
            if(body.getAccountType().equals("current")){
                // how many cuurent account can 1 user have?
                account = accountService.createAccount(account);
            }
            else if(body.getAccountType().equals("saving")){
                List<Account> accounts = accountService.findAllByUserAndAccountType(user,"current");
                if (!accounts.isEmpty()){
                    account = accountService.createAccount(account);
                }
                else
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "To make saving account first you need to make current account");
            }
            else
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "accounts can be type: current or saving");

        }
        else if(user.getRoles().contains(Role.ROLE_ADMIN)){
            //check if user exist
            User userToCreatAccount = userService.getUserModelById(body.getUserId());
            if(userToCreatAccount.getUserId().equals(null)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user does not exist");
            }
            if(body.getAccountType().equals("current")){
                account = accountService.createAccount(account);
            }
            else if(body.getAccountType().equals("saving")){
                List<Account> accounts = accountService.findAllByUserAndAccountType(userToCreatAccount,"current");
                if (!accounts.isEmpty()){
                    account = accountService.createAccount(account);
                }
                else
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "To make saving account first you need to make current account");
            }
            else
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "accounts can be type: current or saving");
        }
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not make account for this user");





        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setIBAN(account.getIBAN());
        accountResponseDTO.setAccountType(account.getAccountType());
        return new ResponseEntity<AccountResponseDTO>(accountResponseDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Account>> getAccounts(@NotNull @Parameter(in = ParameterIn.QUERY, description = "skips the list of users", required = true, schema = @Schema()) @Valid @RequestParam(value = "skip", required = true) Integer skip, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch the needed amount of users", required = false, schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String username = userAuthentication.getName();
        User user = userService.getUserByUsername(username);
        Integer toSkip = skip;
        Integer toLimit = limit;
        if(toSkip.equals(null))
            toSkip = 0;

        if(toLimit.equals(null))
            toLimit=20;

        if(!user.getRoles().contains(Role.ROLE_ADMIN)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "you dont have access");
        }
        List<Account> accounts = accountService.findAllAccount();

       accounts = accounts.stream()
                .skip(toSkip)
                .limit(toLimit)
                .collect(Collectors.toList());
        return new ResponseEntity<List<Account>>(accounts,HttpStatus.OK);
    }

}