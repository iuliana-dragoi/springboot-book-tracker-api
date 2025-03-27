package com.crode.book_tracker_api.service.integration;

import com.crode.book_tracker_api.dto.BookDTO;
import com.crode.book_tracker_api.model.Book;
import com.crode.book_tracker_api.repository.BookRepository;
import com.crode.book_tracker_api.service.BookService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testCreateBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        BookDTO createdBook = bookService.createBook(book);
        assertNotNull(createdBook);
        assertEquals("Test Book", createdBook.getTitle());
    }

    @Test
    void testGetById() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        Book savedBook = bookRepository.save(book);

        Book foundBook = bookService.getById(savedBook.getId());
        assertNotNull(foundBook);
        assertEquals(savedBook.getId(), foundBook.getId());
    }

    @Test
    void testGetAllBooks() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        bookRepository.save(book);

        assertTrue(bookService.getAllBooks().size() > 0);
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        Book savedBook = bookRepository.save(book);

        savedBook.setTitle("Updated Book");
        BookDTO updatedBook = bookService.updateBook(savedBook);

        assertEquals("Updated Book", updatedBook.getTitle());
    }

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        Book savedBook = bookRepository.save(book);

        bookService.deleteBook(savedBook.getId());

        assertFalse(bookRepository.existsById(savedBook.getId()));
    }
}
