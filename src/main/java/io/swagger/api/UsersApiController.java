package io.swagger.api;

import io.swagger.model.Account;
import java.math.BigDecimal;
import io.swagger.model.InlineResponse200;
import io.swagger.model.InlineResponse2001;
import io.swagger.model.LoginDTO;
import io.swagger.model.User;
import io.swagger.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<User>> usersGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "skips the list of users" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "skip", required = true) Integer skip,@NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch the needed amount of users" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "limit", required = true) Integer limit,@NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch the users with or with out account" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "withOutAccount", required = true) BigDecimal withOutAccount) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<User>>(objectMapper.readValue("[ {\n  \"remainingDayLimit\" : 500,\n  \"role\" : \"customer\",\n  \"dayLimit\" : 1000,\n  \"fullName\" : \"tommy king\",\n  \"transactionLimit\" : 1000,\n  \"userId\" : 50,\n  \"email\" : \"tommyk@inholland.nl\"\n}, {\n  \"remainingDayLimit\" : 500,\n  \"role\" : \"customer\",\n  \"dayLimit\" : 1000,\n  \"fullName\" : \"tommy king\",\n  \"transactionLimit\" : 1000,\n  \"userId\" : 50,\n  \"email\" : \"tommyk@inholland.nl\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity usersLoginPost(@Parameter(in = ParameterIn.DEFAULT, description = "New account details", required=true, schema=@Schema()) @Valid @RequestBody LoginDTO body) {
        int userId = 13;
        return ResponseEntity.status(200).body(userId);
    }

    public ResponseEntity<User> usersPost(@Parameter(in = ParameterIn.DEFAULT, description = "User to add", schema=@Schema()) @Valid @RequestBody UserDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<User>(objectMapper.readValue("{\n  \"remainingDayLimit\" : 500,\n  \"role\" : \"customer\",\n  \"dayLimit\" : 1000,\n  \"fullName\" : \"tommy king\",\n  \"transactionLimit\" : 1000,\n  \"userId\" : 50,\n  \"email\" : \"tommyk@inholland.nl\"\n}", User.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

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

    public ResponseEntity<User> usersUserIdGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<User>(objectMapper.readValue("{\n  \"remainingDayLimit\" : 500,\n  \"role\" : \"customer\",\n  \"dayLimit\" : 1000,\n  \"fullName\" : \"tommy king\",\n  \"transactionLimit\" : 1000,\n  \"userId\" : 50,\n  \"email\" : \"tommyk@inholland.nl\"\n}", User.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InlineResponse2001> usersUserIdTotalBalanceGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<InlineResponse2001>(objectMapper.readValue("{\n  \"totalAmount\" : 2000\n}", InlineResponse2001.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InlineResponse2001>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<InlineResponse2001>(HttpStatus.NOT_IMPLEMENTED);
    }

}
