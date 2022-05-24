package io.swagger.service;

import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.Role;
import io.swagger.model.User;
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

    public User add(User user) {
        // TODO: CHECK if exists already
        user.setPassword(passwordEncoder.encode(user.getPassword())); //encrypt password
        return userRepository.save(user); // saves and returns a user
    }

    // TODO: method->GET USER BY ID


    // TODO: method->GET ALL USERS
    public List<User> getAllUsers(Integer skip, Integer limit, Integer withoutAccount) {
        // get all users without an account and skip and limit
        if (withoutAccount == 1) {

        }

        // loop limit times and start at skip + 1
//        List<User> users = new ArrayList<>();
//        for (int i = skip + 1; i <= skip + limit; i++) {
//            users.add(userRepository.findById(i).get());
//        }
        return userRepository.findAllByUserIdAfterAndUserIdIsBefore(skip, (skip + limit + 1));

    }

    public void create100RandomUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            String fullname = randomNameGenerator();
            user.setUsername(fullname + i);
            user.setFullname(fullname + i);
            user.setPassword(passwordEncoder.encode("secret"));
            user.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER)));
            this.add(user);
        }
        // save all users
        //userRepository.saveAll(users);

    }

    public String randomNameGenerator() {
        // create a random name
        String name = "";
        for (int i = 0; i < 10; i++) {
            name += (char) (Math.random() * 26 + 'a');
        }
        return name;
    }


}
