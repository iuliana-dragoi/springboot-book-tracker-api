package com.crode.book_tracker_api.controller;

import com.crode.book_tracker_api.model.BookStatus;
import com.crode.book_tracker_api.service.BookService;
import com.crode.book_tracker_api.service.UserBookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/userBooks")
public class UserBookController {

    private UserBookService userBookService;

    private final BookService bookService;

    public UserBookController(UserBookService userBookService, BookService bookService) {
        this.userBookService = userBookService;
        this.bookService = bookService;
    }

    @PostMapping("/addToList")
    public String addToList(@RequestParam("bookId") Long bookId, Model model, Principal principal) {

        try {
            userBookService.addToList(bookId, principal.getName());
            model.addAttribute("successMessage", "Book added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
        }

        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("bookIds", userBookService.getBookIdsByUser(principal.getName()));
        model.addAttribute("addedBook", bookId);
        model.addAttribute("currentUser", principal.getName());

        return "fragments/bookList :: bookContainer";
    }

    @PostMapping("/addToProgress")
    public String addToProgress(@RequestParam("bookId") Long bookId, Model model, Principal principal) {

        try {
            userBookService.addToProgress(bookId, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("toReadBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.TO_READ));
        model.addAttribute("inProgressBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.IN_PROGRESS));
        model.addAttribute("readBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.READ));

        return "fragments/userBookList :: userBookContainer";
    }

    @PostMapping("/updateProgress")
    public String updateProgress(@RequestParam("bookId") Long bookId, @RequestParam("progress") double progress, Model model, Principal principal) {

        System.out.println("Received progress: " + progress);
        try {
            userBookService.updateProgress(bookId, principal.getName(), progress);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("toReadBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.TO_READ));
        model.addAttribute("inProgressBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.IN_PROGRESS));
        model.addAttribute("readBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.READ));

        return "fragments/userBookList :: userBookContainer";
    }

    @PostMapping("/addToRead")
    public String addToRead(@RequestParam("bookId") Long bookId, Model model, Principal principal) {

        try {
            userBookService.addToRead(bookId, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("toReadBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.TO_READ));
        model.addAttribute("inProgressBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.IN_PROGRESS));
        model.addAttribute("readBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.READ));

        return "fragments/userBookList :: userBookContainer";
    }

    @PostMapping("/updateReview")
    public String updateReview(@RequestParam("bookId") Long bookId, @RequestParam("review") String review, Model model, Principal principal) {

        try {
            userBookService.updateReview(bookId, principal.getName(), review);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("toReadBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.TO_READ));
        model.addAttribute("inProgressBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.IN_PROGRESS));
        model.addAttribute("readBooks", userBookService.getBooksByUserAndStatus(principal.getName(), BookStatus.READ));

        return "fragments/userBookList :: userBookContainer";
    }
}
