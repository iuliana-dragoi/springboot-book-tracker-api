package com.crode.book_tracker_api.controller.unit;

import com.crode.book_tracker_api.controller.LibraryController;
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

public class LibraryControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private UserBookService userBookService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private LibraryController libraryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDashboard() {
        when(principal.getName()).thenReturn("testUser");
        String viewName = libraryController.getDashboard(model, principal);
        assertEquals("library", viewName);
    }

    @Test
    void testRefreshLibrary() {
        when(principal.getName()).thenReturn("testUser");
        String viewName = libraryController.refreshLibrary(model, principal);
        assertEquals("fragments/BookList :: bookContainer", viewName);
    }

    @Test
    void testGetMyBooks() {
        when(principal.getName()).thenReturn("testUser");
        String viewName = libraryController.getMyBooks(model, principal);
        assertEquals("fragments/userBookList :: userBookContainer", viewName);
    }
}
