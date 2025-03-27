package com.crode.book_tracker_api.service.integration;

import com.crode.book_tracker_api.model.*;
import com.crode.book_tracker_api.repository.BookRepository;
import com.crode.book_tracker_api.repository.UserBookRepository;
import com.crode.book_tracker_api.repository.UserRepository;
import com.crode.book_tracker_api.service.UserBookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserBookServiceIntegrationTest {

    @Autowired
    private UserBookService userBookService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserBookRepository userBookRepository;

    @Test
    void testAddToList() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        Book savedBook = bookRepository.save(book);

        User user = new User();
        user.setUsername("testUser" + System.currentTimeMillis());
        user.setPassword("password");
        user.setEmail("text" + System.currentTimeMillis() + "@test.com");
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        userBookService.addToList(savedBook.getId(), savedUser.getUsername());
        assertTrue(userBookRepository.findByBookIdAndUserId(savedBook.getId(), savedUser.getId()).isPresent());
    }

    @Test
    void testGetBooksByUserAndStatus() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        Book savedBook = bookRepository.save(book);

        User user = new User();
        user.setUsername("testUser" + System.currentTimeMillis());
        user.setPassword("password");
        user.setEmail("text" + System.currentTimeMillis() + "@test.com");
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        userBookService.addToList(savedBook.getId(), savedUser.getUsername());

        assertTrue(userBookService.getBooksByUserAndStatus(savedUser.getUsername(), BookStatus.TO_READ).size() > 0);
    }

    @Test
    void testAddToProgress() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        Book savedBook = bookRepository.save(book);

        User user = new User();
        user.setUsername("testUser" + System.currentTimeMillis());
        user.setPassword("password");
        user.setEmail("text" + System.currentTimeMillis() + "@test.com");
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        userBookService.addToList(savedBook.getId(), savedUser.getUsername());
        userBookService.addToProgress(savedBook.getId(), savedUser.getUsername());

        assertEquals(BookStatus.IN_PROGRESS, userBookRepository.findByBookIdAndUserId(savedBook.getId(), savedUser.getId()).get().getStatus());
    }

    @Test
    void testAddToRead() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        Book savedBook = bookRepository.save(book);

        User user = new User();
        user.setUsername("testUser" + System.currentTimeMillis());
        user.setPassword("password");
        user.setEmail("text" + System.currentTimeMillis() + "@test.com");
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        userBookService.addToList(savedBook.getId(), savedUser.getUsername());
        userBookService.addToRead(savedBook.getId(), savedUser.getUsername());

        assertEquals(BookStatus.READ, userBookRepository.findByBookIdAndUserId(savedBook.getId(), savedUser.getId()).get().getStatus());
    }
}
