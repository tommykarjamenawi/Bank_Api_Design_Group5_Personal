package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.Account;
import java.math.BigDecimal;

import io.swagger.model.Role;
import io.swagger.model.dto.*;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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


    @org.springframework.beans.factory.annotation.Autowired
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

    // TODO: 5/24/2022 implement get all account for 1 user
    public ResponseEntity<List<Account>> usersUserIdAccountsGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
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

    public ResponseEntity<UserResponseDTO> usersUserIdGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
        UserResponseDTO userResponseDTO = userService.getUserById(userId);
        return new ResponseEntity<UserResponseDTO>(userResponseDTO, HttpStatus.FOUND);
    }

    // TODO: 5/24/2022 get total balance of all accounts for 1 user
    public ResponseEntity<TotalAmountResponseDTO> usersUserIdTotalBalanceGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<TotalAmountResponseDTO>(objectMapper.readValue("{\n  \"totalAmount\" : 2000\n}", TotalAmountResponseDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<TotalAmountResponseDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<TotalAmountResponseDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}