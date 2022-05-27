package io.swagger.service;

import io.swagger.config.MyWebSecurityConfig;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.Account;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.model.dto.UserDTO;
import io.swagger.model.dto.UserResponseDTO;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private MyWebSecurityConfig securityConfig;

    public String login(String username, String password) {
        String token = "";

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            token = jwtTokenProvider.createToken(username, user.getRoles());
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username or password is incorrect");
        }
        return token;
    }

    public UserResponseDTO addExternalUser(UserDTO userDTO) {
        User userExists = userRepository.findByUsername(userDTO.getUsername());
        if (userExists != null) {
            return null;
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFullname(userDTO.getFullname());
        user.setPassword(securityConfig.passwordEncoder().encode(userDTO.getPassword()));
        user.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER)));
        user.setDayLimit(1500.00);
        user.setTransactionLimit(500.00);
        user.setRemainingDayLimit(1500.00);
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        User newUser = userRepository.save(user);
        userResponseDTO.setUser(newUser);
        return userResponseDTO;
    }

    // this method is only used on startup to create 100 dummy users
    public User add(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        user.setDayLimit(1500.00);
        user.setTransactionLimit(500.00);
        user.setRemainingDayLimit(1500.00);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //encrypt password
        return userRepository.save(user); // saves and returns a user
    }

    public UserResponseDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUser(user);
        return userResponseDTO;
    }

    public User getUserModelById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<User> getAllUsers(Integer skip, Integer limit, Integer withoutAccount) {
        // get all users without an account and skip and limit
        if (withoutAccount == 1) {
            // find users with no connected account
            return userRepository.findAllByAccountsIsNull();
        }
        return userRepository.findAllByUserIdAfterAndUserIdIsBefore(skip, (skip + limit + 1));
    }

    public void create100RandomUsers() {
        // TODO: improve performance by creating 100 users at once
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            User user = new User();
            String fullname = randomNameGenerator();
            user.setUsername(fullname + i);
            user.setFullname(fullname);
            user.setPassword(passwordEncoder.encode("secret"));
            user.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER)));
            //users.add(user);
            this.add(user);
        }
        // save each user in the list (will not work for some reason)
//        for (User user : users) {
//            userRepository.save(user);
//        }
//        users.forEach(user -> userRepository.save(user));
//        userRepository.saveAll(users);
    }

    public User getUserFromToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        return userRepository.findByUsername(username);
    }

    public Double getUserTotalBalance(User user) {
        return accountRepository.getSumOfAllAccounts(user);
    }

    public String randomNameGenerator() {
        // create a random name
        String name = "";
        for (int i = 0; i < 10; i++) {
            name += (char) (Math.random() * 26 + 'a');
        }
        return name;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String getUsernameFromToken(String token) {
        return jwtTokenProvider.getUsername(token);
    }
}