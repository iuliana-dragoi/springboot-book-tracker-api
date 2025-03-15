package com.crode.book_tracker_api.controller;
import com.crode.book_tracker_api.model.BookStatus;
import com.crode.book_tracker_api.service.BookService;
import com.crode.book_tracker_api.service.UserBookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller // Thymeleaf templates are rendered through a @Controller, not a @RestController.
@RequestMapping("/library")
public class LibraryController {

    private final BookService bookService;

    private final UserBookService userBookService;

    public LibraryController(BookService bookService, UserBookService userBookService) {
        this.bookService = bookService;
        this.userBookService = userBookService;
    }

    @GetMapping("")
    public String getDashboard(Model model, Principal principal) {

        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("toReadBooks", userBookService.getBooksByStatus(BookStatus.TO_READ));
        model.addAttribute("inProgressBooks", userBookService.getBooksByStatus(BookStatus.IN_PROGRESS));
        model.addAttribute("readBooks", userBookService.getBooksByStatus(BookStatus.READ));
        model.addAttribute("bookIds", userBookService.getBooksByUser(principal.getName()));

        return "library"; // Thymeleaf template name
    }
}
