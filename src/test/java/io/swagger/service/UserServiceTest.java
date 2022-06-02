package io.swagger.service;

import io.swagger.Swagger2SpringBoot;
import io.swagger.model.*;
import io.swagger.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserService.class)
public class UserServiceTest {

    @MockBean // pretends if you have a bean already
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    User abhi;
    Account account;
    Transaction transaction;

    @Before
    public void setup() {
        abhi = new User();
        abhi.setUserId(1);
        abhi.setFullname("Abhishek Narvekar");
        abhi.setUsername("abhi");
        abhi.setRoles(Arrays.asList(Role.ROLE_USER, Role.ROLE_ADMIN));
        passwordEncoder = new BCryptPasswordEncoder();
        abhi.setPassword(passwordEncoder.encode("sec"));

        account = new Account();
        account.setIBAN("NL34INHO024200001");
        account.setUser(abhi);
        account.setCurrentBalance(34.00);
        account.setAccountType(AccountType.current);

        transaction = new Transaction();
        transaction.setTransactionId(1);
        transaction.setUserPerforming(abhi);
        transaction.setFromAccount("");
        transaction.setToAccount("");
        transaction.setAmount(200.00);
        transaction.setTransactionType(TransactionType.transfer);
        LocalDate date =  LocalDate.of(2021, 02, 12);
        transaction.setTimestamp(date);
    }

    @Test
    public void checkLoginTokenIsNotNull() {
        when(this.userRepository.findByUsername(abhi.getUsername())).thenReturn(abhi);

        String token = userService.login("abhi", "sec");
        assertNotNull(token);
    }



    //@Test
    /*void responseStatusExceptionIfInvalidPassword() {

        Exception exception = assertThrow(ResponseStatusException.class, () -> {
            userService.login("abhi", "vkjdbv");
        });
    }*/
}