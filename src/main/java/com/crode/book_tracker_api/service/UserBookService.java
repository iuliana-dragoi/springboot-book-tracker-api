package com.crode.book_tracker_api.service;

import com.crode.book_tracker_api.model.Book;
import com.crode.book_tracker_api.model.BookStatus;
import com.crode.book_tracker_api.model.User;
import com.crode.book_tracker_api.model.UserBook;
import com.crode.book_tracker_api.repository.BookRepository;
import com.crode.book_tracker_api.repository.UserBookRepository;
import com.crode.book_tracker_api.repository.UserRepository;
import org.springframework.stereotype.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserBookService {

    private final BookRepository bookRepository;

    private final UserBookRepository userBookRepository;

    private final UserRepository userRepository;


    public UserBookService(UserBookRepository userBookRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.userBookRepository = userBookRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<UserBook> getBooksByUserAndStatus(String username, BookStatus status) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userBookRepository.findByUserIdAndStatus(user.getId(), status);
    }

    public void addToList(Long bookId, String username) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userBookRepository.findByBookIdAndUserId(bookId, user.getId()).ifPresent(userBook -> {
            throw new IllegalArgumentException("Book is already in the user's list");
        });

        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setStatus(BookStatus.TO_READ);
        userBookRepository.save(userBook);
    }


    public void addToProgress(Long bookId, String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserBook userBook = userBookRepository.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(() -> new RuntimeException("The book is not in the user's list"));

        userBook.setStatus(BookStatus.IN_PROGRESS);

        userBookRepository.save(userBook);
    }


    public void addToRead(Long bookId, String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserBook userBook = userBookRepository.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(() -> new RuntimeException("The book is not in the user's list"));

        userBook.setStatus(BookStatus.READ);

        userBookRepository.save(userBook);
    }


    public Set<Long> getBookIdsByUser(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserBook> userBooks = userBookRepository.findByUserId(user.getId());
        return userBooks.stream()
            .map(userBook -> userBook.getBook().getId())
            .collect(Collectors.toSet());
    }

    public List<UserBook> getBookByUser(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

       return userBookRepository.findByUserId(user.getId());
    }
}
