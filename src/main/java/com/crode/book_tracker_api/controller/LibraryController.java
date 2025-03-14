package com.crode.book_tracker_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @GetMapping("")
    public String dashboard() {
        return "Welcome to the Book Library!";
    }
}
