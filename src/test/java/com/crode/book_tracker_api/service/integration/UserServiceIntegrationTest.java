package com.crode.book_tracker_api.service.integration;

import com.crode.book_tracker_api.dto.UserRegisterDTO;
import com.crode.book_tracker_api.exceptions.UserAlreadyExistException;
import com.crode.book_tracker_api.model.User;
import com.crode.book_tracker_api.repository.UserRepository;
import com.crode.book_tracker_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testRegisterNewUserAccount() throws UserAlreadyExistException {
        UserRegisterDTO userDto = new UserRegisterDTO();
        userDto.setUsername("newUser");
        userDto.setEmail("newuser@example.com");
        userDto.setPassword("password123");

        User user = userService.registerNewUserAccount(userDto);
        assertNotNull(user);
        assertEquals("newUser", user.getUsername());
    }

    @Test
    void testRegisterUserWithExistingEmail() {
        UserRegisterDTO userDto = new UserRegisterDTO();
        userDto.setUsername("user1" + System.currentTimeMillis());
        userDto.setEmail("user1" + System.currentTimeMillis() + "@example.com");
        userDto.setPassword("password123");

        // Create the user first
        userService.registerNewUserAccount(userDto);

        // Try registering with the same email
        assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUserAccount(userDto));
    }

    @Test
    void testRegisterUserWithExistingUsername() {
        UserRegisterDTO userDto = new UserRegisterDTO();
        userDto.setUsername("user1" + System.currentTimeMillis());
        userDto.setEmail("user1" + System.currentTimeMillis() + "@example.com");
        userDto.setPassword("password123");

        // Create the user first
        userService.registerNewUserAccount(userDto);

        // Try registering with the same username
        userDto.setEmail("user3@example.com");
        assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUserAccount(userDto));
    }
}
