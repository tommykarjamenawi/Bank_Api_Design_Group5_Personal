package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDTO {
    private String fullname;
    private String password;
    private Integer createEmployee;
    private Double dayLimit;
    private Double transactionLimit;
}
