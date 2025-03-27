package com.crode.book_tracker_api.dto;

public class CreateBookDTO {
    private String title;
    private String author;
    private String description;

    public CreateBookDTO() {}

    public CreateBookDTO(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
}

