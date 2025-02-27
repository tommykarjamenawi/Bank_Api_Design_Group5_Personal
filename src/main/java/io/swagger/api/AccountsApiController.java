package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.*;
import io.swagger.model.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    private TransactionService transactionService;


    @Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> accountsIBANDelete(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "IBAN of a user", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN) {
        User user = loggedInUser();
        //receiving the account form database
        Account account = accountService.findByIBAN(IBAN);


        if(account.getAccountType().equals(AccountType.bank))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this bank account");

        if(user.getRoles().contains(Role.ROLE_ADMIN) || user.getAccounts().contains(account)){
            if(account.getAccountType().equals(AccountType.current)){
                List<Account> savingAccounts = accountService.findAllByUserAndAccountType(account.getUser(),AccountType.saving);
                if(savingAccounts.isEmpty()){
                    accountService.deleteAccount(account);
                }else {
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You can not delete your currect account if you have a saving account.");
                }
            }
            else {
                accountService.deleteAccount(account);
            }

        }
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't delete other users account");



        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<AccountResponseDTO> accountsIBANGet(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "IBAN of a user", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN) {
        // getes the data of a user from the token
        User user = loggedInUser();

        //receiving the account form database
        Account account = accountService.findByIBAN(IBAN);
       AccountResponseDTO accountResponseDTO = changeAccoutToAccountResponseDTO(account);
        //check if the user is owner of the account or admin(employee)
        if(user.getRoles().contains(Role.ROLE_ADMIN) || user.getAccounts().contains(account)){
            return new ResponseEntity<AccountResponseDTO>(accountResponseDTO, HttpStatus.OK);
        }
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You dont have authorization to get this account information");

    }


    public ResponseEntity<List<TransactionResponseDTO>> accountsIBANTransactionsGet(
            @Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required = true, schema = @Schema()) @PathVariable("IBAN") String IBAN,
            @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction from start date", required = true, schema = @Schema()) @Valid @RequestParam(value = "startDate", required = true) String startDate,
            @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction till end date", required = true, schema = @Schema()) @Valid @RequestParam(value = "endDate", required = true) String endDate,
            @NotNull @Parameter(in = ParameterIn.QUERY, description = "skip Value", required = true, schema = @Schema())
            @Valid @RequestParam(value = "skip", required = true) Integer skipValue,
            @NotNull @Parameter(in = ParameterIn.QUERY, description = "limit Value", required = true, schema = @Schema())
            @Valid @RequestParam(value = "limit", required = true) Integer limitValue) {

        // getes the data of a user from the token
        User user = loggedInUser();

        if (startDate.equals(null) || endDate.equals(null)) {
            if (startDate.equals(null) && endDate.equals(null)) {
                LocalDate startdate = LocalDate.now();
                LocalDate enddate = LocalDate.now();
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "date range must be specified");
            }
        }
        //receiving the account form database
        Account account = accountService.findByIBAN(IBAN);

        if(user != account.getUser()) {
            if (!user.getRoles().contains(Role.ROLE_ADMIN)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "you are not authorized to see this transaction");
            }
        }
        LocalDate startdate;
        LocalDate enddate;
        try {
            startdate = LocalDate.parse(startDate);
            enddate = LocalDate.parse(endDate);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid date format, needs to be in yyyy-MM-dd");
        }

        List<TransactionResponseDTO> transactions = transactionService.
                findAllTransactionsByIBANAccount(IBAN, startdate, enddate);

        transactions = transactions.stream()
                .skip(skipValue)
                .limit(limitValue)
                .collect(Collectors.toList());
        return new ResponseEntity<List<TransactionResponseDTO>>(transactions, HttpStatus.OK);
    }

    public ResponseEntity<AccountResponseDTO> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "New account details", schema = @Schema()) @Valid @RequestBody AccountDTO body) {

        // getes the data of a user from the token
        User user = loggedInUser();

        //initializing object of AccountResponseDto
        AccountResponseDTO accountResponseDTO;

        // check if user is admin
         if(user.getRoles().contains(Role.ROLE_ADMIN)){
            //check if user exist
            User userToCreatAccount = userService.getUserModelById(body.getUserId());
            if(userToCreatAccount.getUserId().equals(null)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user does not exist");
            }
            accountResponseDTO = checkAndCreateAccount(userToCreatAccount , body);
        }
        else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not make account for this user");


        return new ResponseEntity<AccountResponseDTO>(accountResponseDTO, HttpStatus.OK);
    }

    public ResponseEntity<List<Account>> getAccounts(@NotNull @Parameter(in = ParameterIn.QUERY, description = "skips the list of users", required = true, schema = @Schema()) @Valid @RequestParam(value = "skip", required = true) Integer skip, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch the needed amount of users", required = false, schema = @Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit) {

        // getes the data of a user from the token
        User user = loggedInUser();

        Integer toSkip = skip;
        Integer toLimit = limit;
        if(toSkip.equals(null))
            toSkip = 0;

        if(toLimit.equals(null))
            toLimit=20;

        if(!user.getRoles().contains(Role.ROLE_ADMIN)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "you dont have access");
        }
        List<Account> accounts = accountService.findAllByAccountIdAfterAndAccountIdBefore();

        accounts = accounts.stream()
                .skip(toSkip)
                .limit(toLimit)
                .collect(Collectors.toList());
        return new ResponseEntity<List<Account>>(accounts,HttpStatus.OK);
    }


    private  boolean checkifCurrentAccountExist(User user) {
        //check if the user have already a current account
        Account currentAccount = accountService.findByUserAndAccountType(user, AccountType.current);
        if (currentAccount != null) {
            return true;
        }
        return false;
    }

    private User loggedInUser(){
        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String username = userAuthentication.getName();
        return userService.getUserByUsername(username);
    }

    private AccountResponseDTO checkAndCreateAccount(User user ,AccountDTO body){
       // initialize object account
        Account account = new Account();
        account.setIBAN(account.generateIBAN());
        account.setUser(userService.getUserModelById(body.getUserId()));

        if(!body.getAccountType().toLowerCase().equals(AccountType.current.toString()) && !body.getAccountType().toLowerCase().equals(AccountType.saving.toString())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "accounts can be type: current or saving");
        }
        account.setAccountType(AccountType.valueOf(body.getAccountType().toLowerCase()));
        if(body.getAccountType().toLowerCase().equals(AccountType.current.toString())){
            // a user can have 1 current account a maltiple saving account
            if(checkifCurrentAccountExist(user)){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "You already have current account");
            }
            account = accountService.createAccount(account);
        }
        else{
            List<Account> accounts = accountService.findAllByUserAndAccountType(user,AccountType.current);
            if (!accounts.isEmpty()){
                account = accountService.createAccount(account);
            }
            else
                throw new ResponseStatusException(HttpStatus.CONFLICT, "To make saving account first you need to make current account");
        }
        Account accountRegistered = accountService.findByIBAN(account.getIBAN());

        return changeAccoutToAccountResponseDTO(accountRegistered);
    }

    private AccountResponseDTO changeAccoutToAccountResponseDTO(Account accountRegistered){

        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setIBAN(accountRegistered.getIBAN());
        accountResponseDTO.setAccountType(accountRegistered.getAccountType().toString());
        accountResponseDTO.setAccountId(accountRegistered.getAccountId());
        accountResponseDTO.setAbsoluteLimit(accountRegistered.getAbsoluteLimit());
        accountResponseDTO.setUserId(accountRegistered.getUser().getUserId());
        accountResponseDTO.setCurrentBalance(accountRegistered.getCurrentBalance());
        return accountResponseDTO;
    }

    public ResponseEntity<Void> updateAbsoluteLimitPost(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "IBAN of a user", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN, @Valid @RequestBody AbsoluteLimitDTO body) {
        // getes the data of a user from the token
        User user = loggedInUser();

        if(!user.getRoles().contains(Role.ROLE_ADMIN)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "you dont have access");
        }

        Account account = accountService.findByIBAN(IBAN);
        account.setAbsoluteLimit(body.getAbsoluteLimit());

        accountService.createAccount(account);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<List<TransactionResponseDTO>> accountsIBANTransactionsByAmountGet(
            @Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required=true, schema=@Schema())
            @PathVariable("IBAN") String IBAN, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction by amount" ,required=true,schema=@Schema())
            @Valid @RequestParam(value = "amount", required = true) Double amount,
            @NotNull @Parameter(in = ParameterIn.QUERY, description = "enter operator [<, ==, >]" ,required=true,schema=@Schema())
            @Valid @RequestParam(value = "operator", required = true) String operator,
            @Valid @RequestParam(value = "skip", required = true) Integer skipValue,
            @Valid @RequestParam(value = "limit", required = true) Integer limitValue) {

        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String username = userAuthentication.getName();
        User user = userService.getUserByUsername(username);

        Account userAccount = accountService.findByIBAN(IBAN);

        List<TransactionResponseDTO> transactions = new ArrayList<>();

        if (operator.equals("<")) {
            transactions = transactionService.findAllTransactionsLessThanAmount(IBAN, amount);
        }
        else if (operator.equals(">")) {
            transactions = transactionService.findAllTransactionsGreaterThanAmount(IBAN, amount);
        }
        else if (operator.equals("=")) {
            transactions = transactionService.findAllTransactionEqualToAmount(IBAN, amount);
        }

        transactions = transactions.stream()
                .skip(skipValue)
                .limit(limitValue)
                .collect(Collectors.toList());
        return new ResponseEntity<List<TransactionResponseDTO>>(transactions, HttpStatus.OK);
    }

    public ResponseEntity<List<TransactionResponseDTO>> getTransactionByToOrFromAccount(
            @Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required = true, schema = @Schema())
            @PathVariable("IBAN") String IBAN,
            @NotNull @Parameter(in = ParameterIn.QUERY, description = "enter 'to' or 'from'", required = true, schema = @Schema())
            @Valid @RequestParam(value = "operator", required = true) String accountValue,
            @Valid @RequestParam(value = "skip", required = true) Integer skipValue,
            @Valid @RequestParam(value = "limit", required = true) Integer limitValue) {

        Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String username = userAuthentication.getName();
        User user = userService.getUserByUsername(username);

        Account userAccount = accountService.findByIBAN(IBAN);
        List<TransactionResponseDTO> transactions = new ArrayList<>();

        if (accountValue.equals("from")) {
            transactions = transactionService.findAllTransactionsByFromAccount(IBAN);
        }
        else if (accountValue.equals("to")) {
            transactions = transactionService.findAllTransactionByToAccount(IBAN);
        }
        else {
            transactions = transactionService.findAllTransactionsByFromAccount(IBAN);
            transactions = transactionService.findAllTransactionByToAccount(IBAN);
        }

        transactions = transactions.stream()
                .skip(skipValue)
                .limit(limitValue)
                .collect(Collectors.toList());

        return new ResponseEntity<List<TransactionResponseDTO>>(transactions, HttpStatus.OK);

    }

}

// todo: check the swaggerui contain some end point that we didnt add