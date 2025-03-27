package com.crode.book_tracker_api.dto;

public class UserBookDTO {
    private Long id;

    private String title;
    private double progress;
    private String review;
    private String status;

    private Long bookId;

    private String username;

    public UserBookDTO(Long id, String title, double progress, String review, String status, Long bookId, String username) {
        this.id = id;
        this.title = title;
        this.progress = progress;
        this.review = review;
        this.status = status;
        this.bookId = bookId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getProgress() {
        return progress;
    }

    public String getReview() {
        return review;
    }

    public String getStatus() {
        return status;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getUsername() {
        return username;
    }
}
