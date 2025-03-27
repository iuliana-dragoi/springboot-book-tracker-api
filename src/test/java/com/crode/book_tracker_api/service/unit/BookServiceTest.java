package com.crode.book_tracker_api.service.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.crode.book_tracker_api.dto.BookDTO;
import com.crode.book_tracker_api.model.Book;
import com.crode.book_tracker_api.repository.BookRepository;
import com.crode.book_tracker_api.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
    }

    @Test
    void getById_ReturnsBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        assertEquals(book, bookService.getById(1L));
    }

    @Test
    void getAllBooks_ReturnsBookDTOList() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        List<BookDTO> books = bookService.getAllBooks();
        assertEquals(1, books.size());
    }

    @Test
    void createBook_SavesAndReturnsBookDTO() {
        when(bookRepository.save(book)).thenReturn(book);
        assertNotNull(bookService.createBook(book));
    }
}
