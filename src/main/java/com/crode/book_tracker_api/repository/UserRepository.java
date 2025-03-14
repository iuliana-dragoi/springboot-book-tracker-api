package com.crode.book_tracker_api.repository;

import com.crode.book_tracker_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findUserByUsername(String username);
}
