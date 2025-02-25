package com.crode.book_tracker_api.repository;

import com.crode.book_tracker_api.model.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
}
