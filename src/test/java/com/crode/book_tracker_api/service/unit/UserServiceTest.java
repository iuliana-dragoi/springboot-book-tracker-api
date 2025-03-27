package com.crode.book_tracker_api.service.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.crode.book_tracker_api.dto.UserRegisterDTO;
import com.crode.book_tracker_api.exceptions.UserAlreadyExistException;
import com.crode.book_tracker_api.model.*;
import com.crode.book_tracker_api.repository.*;
import com.crode.book_tracker_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void registerNewUserAccount_Success() throws UserAlreadyExistException {
        UserRegisterDTO userDto = new UserRegisterDTO();
        userDto.setUsername("testUser");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");

        when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.findUserByUsername("testUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> userService.registerNewUserAccount(userDto));
    }

    @Test
    void registerNewUserAccount_EmailExists() {
        UserRegisterDTO userDto = new UserRegisterDTO();
        userDto.setEmail("test@example.com");

        when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUserAccount(userDto));
    }

    @Test
    void registerNewUserAccount_UsernameExists() {
        UserRegisterDTO userDto = new UserRegisterDTO();
        userDto.setUsername("testUser");

        when(userRepository.findUserByUsername("testUser")).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUserAccount(userDto));
    }
}
