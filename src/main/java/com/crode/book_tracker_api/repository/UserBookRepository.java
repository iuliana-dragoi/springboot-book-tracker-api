package com.crode.book_tracker_api.repository;

import com.crode.book_tracker_api.model.BookStatus;
import com.crode.book_tracker_api.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    List<UserBook> findByStatus(BookStatus status);

    Optional<UserBook> findByBookId(Long bookId);

    List<UserBook> findByUserId(Long userId);

}
