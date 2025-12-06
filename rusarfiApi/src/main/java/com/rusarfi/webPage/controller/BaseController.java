package com.rusarfi.webPage.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {

    @ModelAttribute
    public void addRequestPathToModel(HttpServletRequest request, Model model) {
        model.addAttribute("currentPath", request.getRequestURI());
    }
}