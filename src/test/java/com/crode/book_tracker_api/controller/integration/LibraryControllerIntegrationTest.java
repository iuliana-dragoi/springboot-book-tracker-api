package com.crode.book_tracker_api.controller.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LibraryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetDashboard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("library"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("books", "bookIds", "currentUser"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testRefreshLibrary() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/refresh"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("fragments/BookList :: bookContainer"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("books", "bookIds", "currentUser"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetMyBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/getMyBooks")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("fragments/userBookList :: userBookContainer"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("toReadBooks", "inProgressBooks", "readBooks"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/library/deleteBook")
            .param("bookId", "1")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("fragments/BookList :: bookContainer"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("successMessage", "deletedBook"))
        .andExpect(MockMvcResultMatchers.model().attribute("successMessage", "Book deleted successfully!"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetBookTab() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/library/createBook")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("fragments/createBook :: createBookContainer"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("bookDTO"));
    }
}
