package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.Role;
import io.swagger.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserTotalBalanceResponseDTO {
    @JsonProperty("totalBalance")
    private Double totalBalance;
}