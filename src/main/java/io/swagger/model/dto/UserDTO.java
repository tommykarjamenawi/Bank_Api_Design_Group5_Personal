package io.swagger.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-13T15:15:19.174Z[GMT]")

@Getter
@Setter
@NoArgsConstructor
public class UserDTO   {
  @JsonProperty("username")
  private String username = null;

  @JsonProperty("fullname")
  private String fullname = null;

  @JsonProperty("password")
  private String password = null;

  private Integer createEmployee = null;

//  @JsonProperty("roles")
//  private List<Role> roles = null;
}