package com.crode.book_tracker_api.controller.integration;

import com.crode.book_tracker_api.dto.BookDTO;
import com.crode.book_tracker_api.dto.CreateBookDTO;
import com.crode.book_tracker_api.model.Book;
import com.crode.book_tracker_api.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookService bookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book("Spring Boot Testing", "John Doe", "A book on testing");
        bookService.createBook(testBook);
    }

    @BeforeEach
    void setupAuthentication() {
        UserDetails user = User.withUsername("admin")
                .password("password")
                .roles("ADMIN") // Must match @PreAuthorize("hasRole('ROLE_ADMIN')")
                .build();

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        SecurityContextHolder.setContext(context);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createBook_shouldReturnCreatedBook() throws Exception {
        CreateBookDTO newBook = new CreateBookDTO("New Book", "Jane Doe", "A new description");

        ResultActions result = mockMvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook))
                .with(SecurityMockMvcRequestPostProcessors.csrf()) // 🔹 Include CSRF token
                .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))

        ).andExpect(status().isCreated());

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.author").value("Jane Doe"))
                .andExpect(jsonPath("$.description").value("A new description"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateBook_shouldReturnUpdatedBook() throws Exception {
        BookDTO updatedBook = new BookDTO(testBook.getId(), "Updated Title", "Updated Author", "Updated Description", new ArrayList<>());

        ResultActions result = mockMvc.perform(put("/book/" + testBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook))
                .with(SecurityMockMvcRequestPostProcessors.csrf()) // 🔹 Include CSRF token
                .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.author").value("Updated Author"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateBook_shouldReturnNotFoundForInvalidId() throws Exception {
        BookDTO updatedBook = new BookDTO(999L, "Title", "Author", "Description", new ArrayList<>());

        mockMvc.perform(put("/book/999")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedBook))
            .with(SecurityMockMvcRequestPostProcessors.csrf()) // 🔹 Include CSRF token
            .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
        ).andExpect(status().isNotFound());
    }
}