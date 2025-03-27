package com.crode.book_tracker_api.controller.unit;

import com.crode.book_tracker_api.controller.BookController;
import com.crode.book_tracker_api.dto.BookDTO;
import com.crode.book_tracker_api.dto.CreateBookDTO;
import com.crode.book_tracker_api.model.Book;
import com.crode.book_tracker_api.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook() {
        CreateBookDTO createBookDTO = new CreateBookDTO("Test Title", "Test Author", "Test Description");
        Book book = new Book("Test Title", "Test Author", "Test Description");
        BookDTO bookDTO = new BookDTO(1L, "Test Title", "Test Author", "Test Description", new ArrayList<>());

        when(bookService.createBook(any(Book.class))).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookController.createBook(createBookDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test Title", response.getBody().getTitle());
    }

    @Test
    void testUpdateBook() {
        BookDTO bookDTO = new BookDTO(1L, "Updated Title", "Updated Author", "Updated Description", new ArrayList<>());
        Book book = new Book("Old Title", "Old Author", "Old Description");
        when(bookService.getById(1L)).thenReturn(book);
        when(bookService.updateBook(any(Book.class))).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookController.updateBook(1L, bookDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Title", response.getBody().getTitle());
    }
}
