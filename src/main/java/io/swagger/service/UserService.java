package io.swagger.service;

import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){

        if (userRepository.findByEmail(user.getEmail())==null){
            return userRepository.save(user);
        }
        else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email address already being used.");
        }

    }

    public Optional<User> getUserById(Integer Id){
        return userRepository.findById(Id);
    }

    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }



}
