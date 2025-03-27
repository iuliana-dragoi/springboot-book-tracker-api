package com.crode.book_tracker_api.dto;

import com.crode.book_tracker_api.model.BookStatus;

public class UserBookDetailsDTO {

    private Long id;
    private String review;
    private String username;

    private BookStatus status;

    public UserBookDetailsDTO(Long id, String review, String username, BookStatus status) {
        this.id = id;
        this.review = review;
        this.username = username;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public String getUsername() {
        return username;
    }

    public BookStatus getStatus() {
        return status;
    }
}

