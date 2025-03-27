package com.crode.book_tracker_api.dto;

import java.util.List;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String description;
    private List<UserBookDetailsDTO> userBookDetails;

    public BookDTO(Long id, String title, String author, String description, List<UserBookDetailsDTO> userBookDetails) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.userBookDetails = userBookDetails;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public List<UserBookDetailsDTO> getUserBookDetails() {
        return userBookDetails;
    }
}
