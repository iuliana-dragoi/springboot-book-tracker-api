package com.crode.book_tracker_api.controller.integration;

import com.crode.book_tracker_api.dto.UserRegisterDTO;
import com.crode.book_tracker_api.exceptions.UserAlreadyExistException;
import com.crode.book_tracker_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void root_whenNotAuthenticated_redirectsToLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void login_returnsLoginView() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void register_returnsRegisterView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void registerUser_whenValidInput_redirectsToLogin() throws Exception {
        Mockito.when(userService.registerNewUserAccount(Mockito.any(UserRegisterDTO.class)))
                .thenReturn(null);

        mockMvc.perform(post("/register")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "testuser")
            .param("password", "password123")
            .param("matchingPassword", "password123")
            .param("email", "test@example.com")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login"));
    }

    @Test
    void registerUser_whenUserExists_redirectsToRegisterWithError() throws Exception {
        Mockito.when(userService.registerNewUserAccount(Mockito.any(UserRegisterDTO.class)))
                .thenThrow(new UserAlreadyExistException("User already exists"));

        mockMvc.perform(post("/register")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "existinguser")
            .param("password", "password123")
            .param("matchingPassword", "password123")
            .param("email", "existing@example.com")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/register"))
        .andExpect(flash().attributeExists("userExistsError"));
    }
}

