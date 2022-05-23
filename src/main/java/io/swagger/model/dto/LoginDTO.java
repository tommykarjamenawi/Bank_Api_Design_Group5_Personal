package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {
    @JsonProperty("username")
    private String username = null;

    @JsonProperty("password")
    private String password = null;

}
