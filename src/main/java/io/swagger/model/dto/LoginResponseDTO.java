package io.swagger.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * InlineResponse200
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDTO {
    private Integer userId;
    private String email;
    private String token; // Returned JWT bearer token
}
