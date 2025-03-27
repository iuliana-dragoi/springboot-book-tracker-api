package com.crode.book_tracker_api.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String errorMessage = "An unexpected error occurred.";

        if (statusCode != null) {
            HttpStatus status = HttpStatus.valueOf(statusCode);
            errorMessage = status.getReasonPhrase();
        }

        model.addAttribute("status", statusCode);
        model.addAttribute("error", "Error " + statusCode);
        model.addAttribute("message", errorMessage);

        return "error/error";
    }
}
