package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * AccountDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")

@Getter
@Setter
public class AccountDTO {
    @JsonProperty("userId")
    private Integer userId = null;

    @JsonProperty("accountType")
    private String accountType = null;
}
