package com.crode.book_tracker_api.service;

import com.crode.book_tracker_api.dto.BookDTO;
import com.crode.book_tracker_api.model.Book;
import com.crode.book_tracker_api.repository.BookRepository;
import com.crode.book_tracker_api.util.EntityToDtoConverter;
import org.springframework.stereotype.*;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(EntityToDtoConverter::convertToBookDTO).toList();
    }

    public BookDTO createBook(Book book) {
        Book savedBook = bookRepository.save(book);
        return EntityToDtoConverter.convertToBookDTO(savedBook);
    }

    public BookDTO updateBook(Book book) {
        Book updatedBook = bookRepository.save(book);
        return EntityToDtoConverter.convertToBookDTO(updatedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
