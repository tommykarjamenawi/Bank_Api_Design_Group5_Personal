/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Transaction;
import io.swagger.model.dto.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")
@Validated
public interface TransactionsApi {

    @Operation(summary = "Gets all transactions", description = "returns the transactions ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "transaction" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Returns all the transactions needed", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))),
        
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),
        
        @ApiResponse(responseCode = "404", description = "No transactions found"),
        
        @ApiResponse(responseCode = "500", description = "Internal server error") })
    @RequestMapping(value = "/transactions",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Transaction>> transactionsGet(
            @Parameter(in = ParameterIn.QUERY, description = "fetch transaction from start date" ,required=true,schema=@Schema())
            @Valid @RequestParam(value = "startDate", required = true) String startDate,
            @Parameter(in = ParameterIn.QUERY, description = "fetch transaction till end date" ,required=true,schema=@Schema())
            @Valid @RequestParam(value = "endDate", required = true) String endDate,
            @Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer fromIndex,
            @Valid @RequestParam(value = "limit", required = false, defaultValue = "100") Integer limit);

    @Operation(summary = "Creates a transaction", description = "Creates a transaction", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "transaction" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Transaction created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))),
        
        @ApiResponse(responseCode = "400", description = "invalid input, object invalid"),
        
        @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),
        
        @ApiResponse(responseCode = "404", description = "Not found"),
        
        @ApiResponse(responseCode = "500", description = "Internal server error") })
    @RequestMapping(value = "/transactions",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Transaction> transactionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody TransactionDTO body) throws Exception;



}

