/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.dto.AccountDTO;
import io.swagger.model.Transaction;
import io.swagger.model.dto.AccountResponseDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")
@Validated
public interface AccountsApi {

    @Operation(summary = "Close account for a user", description = "Delete a user", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "employee" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account closed successfully"),

            @ApiResponse(responseCode = "400", description = "Invalid IBAN format"),

            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),

            @ApiResponse(responseCode = "404", description = "No account found with this IBAN"),

            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @RequestMapping(value = "/accounts/{IBAN}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> accountsIBANDelete(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "IBAN of a user", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN);


    @Operation(summary = "Search for account by IBAN", description = "By passing in the appropriate options, you can search for user data in the system ", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "customer", "employee" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))),

            @ApiResponse(responseCode = "400", description = "Invalid IBAN format"),

            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),

            @ApiResponse(responseCode = "404", description = "User based on this IBAN is not found"),

            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @RequestMapping(value = "/accounts/{IBAN}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Account> accountsIBANGet(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "IBAN of a user", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN);


    @Operation(summary = "Gets all transactions of the account", description = "returns the transaction history ", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "transaction" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all the transactions needed", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))),

            @ApiResponse(responseCode = "400", description = "Bad Request"),

            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),

            @ApiResponse(responseCode = "404", description = "No transactions found"),

            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @RequestMapping(value = "/accounts/{IBAN}/transactions",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Transaction>> accountsIBANTransactionsGet(@Parameter(in = ParameterIn.PATH, description = "Numeric ID of the user to get", required=true, schema=@Schema()) @PathVariable("IBAN") Integer IBAN, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction from start date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "startDate", required = true) String startDate, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction till end date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "endDate", required = true) String endDate, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction from start date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "minValue", required = true) Integer minValue, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch transaction till end date" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "maxValue", required = true) Integer maxValue);

    @Operation(summary = "Creates a new account", description = "Creates a new account. The Iban is being generated on the server.", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "employee", "customer" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))),

            @ApiResponse(responseCode = "400", description = "Invalid account object"),

            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),

            @ApiResponse(responseCode = "404", description = "invalid input, object invalid"),

            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @RequestMapping(value = "/accounts",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<AccountResponseDTO> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "New account details", schema=@Schema()) @Valid @RequestBody AccountDTO body);


    @Operation(summary = "Get all accounts", description = "Gets accounts from the system", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "employee" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns users accounts", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Account.class)))),

            @ApiResponse(responseCode = "400", description = "Invalid account object"),

            @ApiResponse(responseCode = "401", description = "Access token is missing or invalid"),

            @ApiResponse(responseCode = "404", description = "No accounts found"),

            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @RequestMapping(value = "/accounts",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<Account>> getAccounts(@NotNull @Parameter(in = ParameterIn.QUERY, description = "skips the list of users" ,required=false,schema=@Schema()) @Valid @RequestParam(value = "skip", required = false) Integer skip, @NotNull @Parameter(in = ParameterIn.QUERY, description = "fetch the needed amount of users" ,required=false,schema=@Schema()) @Valid @RequestParam(value = "limit", required = false) Integer limit);

}