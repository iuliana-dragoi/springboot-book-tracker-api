package com.crode.book_tracker_api.service.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.crode.book_tracker_api.model.Role;
import com.crode.book_tracker_api.model.User;
import com.crode.book_tracker_api.repository.UserRepository;
import com.crode.book_tracker_api.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    void loadUserByUsername_UserExists() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRole(Role.USER);
        when(userRepository.findUserByUsername("testUser")).thenReturn(Optional.of(user));

        assertNotNull(userDetailsService.loadUserByUsername("testUser"));
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        when(userRepository.findUserByUsername("unknown")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userDetailsService.loadUserByUsername("unknown"));
    }
}
