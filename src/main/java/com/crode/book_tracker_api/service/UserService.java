package com.crode.book_tracker_api.service;

import com.crode.book_tracker_api.dto.UserDto;
import com.crode.book_tracker_api.exceptions.UserAlreadyExistException;
import com.crode.book_tracker_api.model.User;
import com.crode.book_tracker_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getEmail());
        }
        if (usernameExists(userDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that username: " + userDto.getUsername());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findUserByEmail(email).orElse(null) != null;
    }

    private boolean usernameExists(String username) {
        return userRepository.findUserByUsername(username).orElse(null) != null;
    }
}
