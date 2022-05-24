package io.swagger.service;

import io.swagger.model.User;
import io.swagger.model.dto.UserDTO;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailAddress(email);
    }

    public User findById(Integer id) {
        return userRepository.findUserByUserId(id);
    }

}
