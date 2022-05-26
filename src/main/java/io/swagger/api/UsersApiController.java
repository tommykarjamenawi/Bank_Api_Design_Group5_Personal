package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.Account;
import java.math.BigDecimal;

import io.swagger.model.Role;
import io.swagger.model.dto.*;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")
@RestController
@Api(tags = {"employee", "customer", "transaction"})
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;


    @Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<User>> usersGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "skips the list of users" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "skip", required = true) Integer skip, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch the needed amount of users" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "limit", required = true) Integer limit, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch the users with or with out account" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "withOutAccount", required = true) Integer withOutAccount) {
        List<User> users = userService.getAllUsers(skip, limit, withOutAccount);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    public LoginResponseDTO usersLoginPost(@Parameter(in = ParameterIn.DEFAULT, description = "New account details", required=true, schema=@Schema()) @Valid @RequestBody LoginDTO body) {
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(userService.login(body.getUsername(), body.getPassword()));
        return responseDTO;
    }

    public ResponseEntity<UserResponseDTO> usersPost(@Parameter(in = ParameterIn.DEFAULT, description = "User to add", schema=@Schema()) @Valid @RequestBody UserDTO body) {
        UserResponseDTO userResponseDTO = userService.addExternalUser(body);
        if (userResponseDTO == null) {
            // user already exists
            return new ResponseEntity<UserResponseDTO>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UserResponseDTO>(userResponseDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Account>> usersUserIdAccountsGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
//        // get token from header
//        String token = request.getHeader("Authorization");
//        // get user from token
//        User user = userService.getUserFromToken(token);

        // get user from userId
        User user = userService.getUserModelById(userId);

        // get all account of the user
        List<Account> accounts = accountService.getAllAccountsOfUser(user);

        return new ResponseEntity<List<Account>>(accounts, HttpStatus.FOUND);
    }

    public ResponseEntity<UserResponseDTO> usersUserIdGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
        UserResponseDTO userResponseDTO = userService.getUserById(userId);
        return new ResponseEntity<UserResponseDTO>(userResponseDTO, HttpStatus.FOUND);
    }

    public ResponseEntity<UserTotalBalanceResponseDTO> usersUserIdTotalBalanceGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {

        User user = userService.getUserModelById(userId);
        Double totalBalance = userService.getUserTotalBalance(user);
        if (totalBalance == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User has no account");
        }
        UserTotalBalanceResponseDTO userTotalBalanceResponseDTO = new UserTotalBalanceResponseDTO();
        userTotalBalanceResponseDTO.setTotalBalance(totalBalance);

        return new ResponseEntity<UserTotalBalanceResponseDTO>(userTotalBalanceResponseDTO, HttpStatus.OK);
    }

}