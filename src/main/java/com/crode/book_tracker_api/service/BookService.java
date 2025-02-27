package com.crode.book_tracker_api.service;

import com.crode.book_tracker_api.model.Book;
import com.crode.book_tracker_api.repository.BookRepository;
import org.springframework.stereotype.*;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deletebook(Long id) {
        bookRepository.deleteById(id);
    }
}
