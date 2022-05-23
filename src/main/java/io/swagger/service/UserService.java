package io.swagger.service;

import io.swagger.jwt.JwtTokenProvider;
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
        }
        catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username or password is incorrect");
        }
        return token;
    }

    public User add(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword())); //encrypt password
        return userRepository.save(user); // saves and returns a user
    }

}
