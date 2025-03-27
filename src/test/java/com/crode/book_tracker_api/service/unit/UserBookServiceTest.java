package com.crode.book_tracker_api.service.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.crode.book_tracker_api.model.Book;
import com.crode.book_tracker_api.model.User;
import com.crode.book_tracker_api.model.BookStatus;
import com.crode.book_tracker_api.repository.BookRepository;
import com.crode.book_tracker_api.repository.UserBookRepository;
import com.crode.book_tracker_api.repository.UserRepository;
import com.crode.book_tracker_api.service.UserBookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserBookServiceTest {

    @Mock
    private UserBookRepository userBookRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserBookService userBookService;

    @Test
    void getBooksByUserAndStatus_UserFound() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findUserByUsername("testUser")).thenReturn(Optional.of(user));
        when(userBookRepository.findByUserIdAndStatus(1L, BookStatus.TO_READ)).thenReturn(List.of());

        assertNotNull(userBookService.getBooksByUserAndStatus("testUser", BookStatus.TO_READ));
    }

    @Test
    void addToList_AddsBookToUser() {
        Book book = new Book();
        book.setId(1L);
        User user = new User();
        user.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findUserByUsername("testUser")).thenReturn(Optional.of(user));
        when(userBookRepository.findByBookIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> userBookService.addToList(1L, "testUser"));
    }
}

