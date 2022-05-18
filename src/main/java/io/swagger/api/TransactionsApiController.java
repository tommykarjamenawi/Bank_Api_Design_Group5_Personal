package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.Transaction;
import io.swagger.model.dto.TransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.TransactionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.threeten.bp.LocalDate;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")
@RestController
@Api(tags = {"employee, customer, transaction"})
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private TransactionService transactionService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Transaction>> transactionsGet(@NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction from start date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "startDate", required = true) String startDate,@NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction till end date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "endDate", required = true) String endDate) {


        return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Transaction> transactionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody TransactionDTO body) {

        Transaction transaction = new Transaction();
        transaction.setAmount(body.getAmount());
        transaction.setFromAccount(body.getFromAccount());
        transaction.setTimestamp(LocalDate.now());
        transaction.setToAccount(body.getToAccount());
        transaction.setTransactionId(1);
        transaction.setTransactionType(body.getTransactionType());
        transaction.setUserPerformingId(2);

        Transaction storeTransaction =  transactionService.createTransaction(transaction);
        return new ResponseEntity<Transaction>(storeTransaction, HttpStatus.OK);
    }

}
