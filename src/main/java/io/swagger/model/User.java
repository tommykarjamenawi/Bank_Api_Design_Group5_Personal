package io.swagger.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.util.List;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Log
public class User {
    @Id
    @SequenceGenerator(name = "users_seq", initialValue = 1)
    @GeneratedValue(generator = "users_seq", strategy = GenerationType.SEQUENCE)
    private Integer userId;
    private String fullname;
    private String username;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
    private String password;
    private Double dayLimit;
    private Double transactionLimit;
    private Double remainingDayLimit;
}