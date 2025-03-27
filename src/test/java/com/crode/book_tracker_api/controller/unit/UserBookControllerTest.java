package com.crode.book_tracker_api.controller.unit;

import com.crode.book_tracker_api.controller.UserBookController;
import com.crode.book_tracker_api.service.BookService;
import com.crode.book_tracker_api.service.UserBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.security.Principal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserBookControllerTest {

    @Mock
    private UserBookService userBookService;

    @Mock
    private BookService bookService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private UserBookController userBookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddToList() {
        when(principal.getName()).thenReturn("testUser");
        String viewName = userBookController.addToList(1L, model, principal);
        assertEquals("fragments/bookList :: bookContainer", viewName);
    }

    @Test
    void testAddToProgress() {
        when(principal.getName()).thenReturn("testUser");
        String viewName = userBookController.addToProgress(1L, model, principal);
        assertEquals("fragments/userBookList :: userBookContainer", viewName);
    }

    @Test
    void testUpdateProgress() {
        when(principal.getName()).thenReturn("testUser");
        String viewName = userBookController.updateProgress(1L, 50.0, model, principal);
        assertEquals("fragments/userBookList :: userBookContainer", viewName);
    }

    @Test
    void testAddToRead() {
        when(principal.getName()).thenReturn("testUser");
        String viewName = userBookController.addToRead(1L, model, principal);
        assertEquals("fragments/userBookList :: userBookContainer", viewName);
    }

    @Test
    void testUpdateReview() {
        when(principal.getName()).thenReturn("testUser");
        String viewName = userBookController.updateReview(1L, "Great book!", model, principal);
        assertEquals("fragments/userBookList :: userBookContainer", viewName);
    }
}
