package com.crode.book_tracker_api.controller;
import com.crode.book_tracker_api.dto.CreateBookDTO;
import com.crode.book_tracker_api.model.BookStatus;
import com.crode.book_tracker_api.service.BookService;
import com.crode.book_tracker_api.service.UserBookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("bookIds", userBookService.getBookIdsByUser(principal.getName()));
        model.addAttribute("currentUser", principal.getName());

        return "library"; // Thymeleaf template name
    }

    @GetMapping("/refresh")
    public String refreshLibrary(Model model, Principal principal) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("bookIds", userBookService.getBookIdsByUser(principal.getName()));
        model.addAttribute("currentUser", principal.getName());

        return "fragments/BookList :: bookContainer";
    }

    @GetMapping("/geMyBooks")
    public String getMyBooks(Model model, Principal principal) {
        model.addAttribute("toReadBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.TO_READ));
        model.addAttribute("inProgressBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.IN_PROGRESS));
        model.addAttribute("readBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.READ));

        return "fragments/userBookList :: userBookContainer";
    }

    @PostMapping("/deleteBook")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteBook(@RequestParam("bookId") Long bookId, Model model, Principal principal) {
        try {
            bookService.deleteBook(bookId);
            model.addAttribute("successMessage", "Book deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("deletedBook", true);
        return refreshLibrary(model, principal);
    }

    @GetMapping("/createBook")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getBookTab(Model model) {
        model.addAttribute("bookDTO", new CreateBookDTO());
        return "fragments/createBook :: createBookContainer";
    }
}
