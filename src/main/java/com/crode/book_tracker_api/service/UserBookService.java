package com.crode.book_tracker_api.service;

import com.crode.book_tracker_api.model.BookStatus;
import com.crode.book_tracker_api.model.UserBook;
import com.crode.book_tracker_api.repository.UserBookRepository;
import org.springframework.stereotype.*;

import java.util.List;

@Service
public class UserBookService {

    private final UserBookRepository userBookRepository;

    public UserBookService(UserBookRepository userBookRepository) {
        this.userBookRepository = userBookRepository;
    }

    public List<UserBook> getBooksByStatus(BookStatus status) {
        return userBookRepository.findByStatus(status);
    }

}
