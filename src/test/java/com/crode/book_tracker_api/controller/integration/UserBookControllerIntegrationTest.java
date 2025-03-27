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
public class UserBookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddToList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/userBooks/addToList")
            .param("bookId", "2")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("fragments/bookList :: bookContainer"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("successMessage", "books", "bookIds", "addedBook", "currentUser"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void testAddToProgress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/userBooks/addToProgress")
            .param("bookId", "2")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("fragments/userBookList :: userBookContainer"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("toReadBooks", "inProgressBooks", "readBooks"));
    }

    @Test
    @WithMockUser(username = "user2", roles = {"USER"})
    void testUpdateProgress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/userBooks/updateProgress")
            .param("bookId", "2")
            .param("progress", "50")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("fragments/userBookList :: userBookContainer"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("toReadBooks", "inProgressBooks", "readBooks"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void testUpdateReview() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/userBooks/updateReview")
            .param("bookId", "2")
            .param("review", "Great book!")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("fragments/userBookList :: userBookContainer"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("toReadBooks", "inProgressBooks", "readBooks"));
    }
}
