package com.crode.book_tracker_api.controller.unit;

import com.crode.book_tracker_api.controller.AuthController;
import com.crode.book_tracker_api.dto.UserRegisterDTO;
import com.crode.book_tracker_api.exceptions.UserAlreadyExistException;
import com.crode.book_tracker_api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_ReturnsLoginView() {
        String view = authController.login();
        assertEquals("login", view);
    }

    @Test
    void testShowRegistrationForm_AddsUserAttribute_ReturnsRegisterView() {
        String view = authController.showRegistrationForm(model);
        verify(model, times(1)).addAttribute(eq("user"), any(UserRegisterDTO.class));
        assertEquals("register", view);
    }

    @Test
    void testRegisterUser_WithValidationErrors_ReturnsRegisterView() {
        when(bindingResult.hasErrors()).thenReturn(true);
        String view = authController.registerUser(new UserRegisterDTO(), bindingResult, redirectAttributes);
        assertEquals("register", view);
    }

    @Test
    void testRegisterUser_WithExistingUser_RedirectsToRegister() throws UserAlreadyExistException {
        doThrow(new UserAlreadyExistException("User already exists"))
                .when(userService).registerNewUserAccount(any(UserRegisterDTO.class));

        String view = authController.registerUser(new UserRegisterDTO(), bindingResult, redirectAttributes);

        verify(redirectAttributes, times(1)).addFlashAttribute(eq("userExistsError"), eq("User already exists"));
        assertEquals("redirect:/register", view);
    }

    @Test
    void testRegisterUser_Success_RedirectsToLogin() throws UserAlreadyExistException {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.registerNewUserAccount(any(UserRegisterDTO.class))).thenReturn(null);

        String view = authController.registerUser(new UserRegisterDTO(), bindingResult, redirectAttributes);

        verify(redirectAttributes, times(1)).addFlashAttribute(eq("userRegistered"), eq("User registered successfully!"));
        assertEquals("redirect:/login", view);
    }
}