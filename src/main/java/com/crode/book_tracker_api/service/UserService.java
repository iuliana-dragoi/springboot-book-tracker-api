package com.crode.book_tracker_api.service;

import com.crode.book_tracker_api.model.User;
import com.crode.book_tracker_api.repository.UserRepository;
import org.springframework.stereotype.*;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
