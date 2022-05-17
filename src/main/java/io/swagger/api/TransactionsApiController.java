package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.Transaction;
import io.swagger.model.TransactionDTO;
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
@Api(tags = {"employee, customer, transaction"})
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Transaction>> transactionsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction from start date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "startDate", required = true) String startDate,@NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction till end date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "endDate", required = true) String endDate) {
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

    public ResponseEntity<Transaction> transactionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody TransactionDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Transaction>(objectMapper.readValue("{\n  \"transactionType\" : \"bank transfer\",\n  \"toAccount\" : \"NL45INHO9375867856\",\n  \"amount\" : 220.25,\n  \"userPerformingId\" : 50,\n  \"fromAccount\" : \"NL14INHO1234567890\",\n  \"balanceAfterTransfer\" : 100,\n  \"transactionId\" : 13,\n  \"timestamp\" : \"2022-07-21T17:32:28Z\"\n}", Transaction.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Transaction>(HttpStatus.NOT_IMPLEMENTED);
    }

}
