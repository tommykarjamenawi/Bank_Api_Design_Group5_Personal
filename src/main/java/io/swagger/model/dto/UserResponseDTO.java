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
public class UserResponseDTO {
    @JsonProperty("username")
    private String username;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("roles")
    private List<Role> roles;

    public void setUser(User newUser) {
        this.username = newUser.getUsername();
        this.fullname = newUser.getFullname();
        this.roles = newUser.getRoles();
    }
}