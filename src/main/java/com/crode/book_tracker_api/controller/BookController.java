package com.crode.book_tracker_api.controller;

import com.crode.book_tracker_api.dto.BookDTO;
import com.crode.book_tracker_api.dto.CreateBookDTO;
import com.crode.book_tracker_api.model.Book;
import com.crode.book_tracker_api.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookDTO> createBook(@RequestBody CreateBookDTO bookDTO) {
        Book book = new Book(bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getDescription());
        BookDTO savedBook = bookService.createBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDto) {
        Book existingBook = bookService.getById(id);
        if(existingBook != null) {
            existingBook.setAuthor(bookDto.getAuthor());
            existingBook.setDescription(bookDto.getDescription());
            existingBook.setTitle(bookDto.getTitle());

            return new ResponseEntity<>(bookService.updateBook(existingBook), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
