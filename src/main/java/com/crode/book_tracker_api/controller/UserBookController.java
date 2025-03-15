package com.crode.book_tracker_api.controller;

import com.crode.book_tracker_api.service.UserBookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
@RequestMapping("/userBooks")
public class UserBookController {

    private UserBookService userBookService;

    public UserBookController(UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    @PostMapping("/addToList")
    public String addToRead(@RequestParam("bookId") Long bookId, Principal principal, RedirectAttributes redirectAttributes) {
        System.out.println("Received request to add bookId: " + bookId + " for user: " + principal.getName());

        try {
            userBookService.addToList(bookId, principal.getName());
            redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/library";
    }
}
