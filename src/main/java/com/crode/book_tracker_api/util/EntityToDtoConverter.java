package com.crode.book_tracker_api.util;

import com.crode.book_tracker_api.dto.BookDTO;
import com.crode.book_tracker_api.dto.UserBookDTO;
import com.crode.book_tracker_api.dto.UserBookDetailsDTO;
import com.crode.book_tracker_api.model.Book;
import com.crode.book_tracker_api.model.UserBook;

import java.util.ArrayList;
import java.util.List;

public class EntityToDtoConverter {

    public static BookDTO convertToBookDTO(Book book) {
        List<UserBookDetailsDTO> userBookDetails = new ArrayList<>();

        if(book.getUserBooks() != null) {
            userBookDetails = book.getUserBooks().stream()
                .map(userBook -> new UserBookDetailsDTO(userBook.getId(),userBook.getReview(), userBook.getUser().getUsername(), userBook.getStatus())).toList();
        }

        return new BookDTO(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getDescription(),
            userBookDetails
        );
    }

    public static UserBookDTO convertToUserBookDTO(UserBook userBook) {
        return new UserBookDTO(
            userBook.getId(),
            userBook.getBook().getTitle(),
            userBook.getProgress(),
            userBook.getReview(),
            userBook.getStatus().name(),
            userBook.getBook().getId(),
            userBook.getUser().getUsername()
        );
    }
}
