package com.crode.book_tracker_api.repository;

import com.crode.book_tracker_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
