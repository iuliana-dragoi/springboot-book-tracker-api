package com.crode.book_tracker_api.service.integration;

import com.crode.book_tracker_api.model.Role;
import com.crode.book_tracker_api.model.User;
import com.crode.book_tracker_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomUserDetailsServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    void testLoadUserByUsername_UserExists() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("text@test.com");
        user.setRole(Role.USER);
        userRepository.save(user);

        assertNotNull(userDetailsService.loadUserByUsername("testUser"));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        assertThrows(RuntimeException.class, () -> userDetailsService.loadUserByUsername("nonExistingUser"));
    }
}

