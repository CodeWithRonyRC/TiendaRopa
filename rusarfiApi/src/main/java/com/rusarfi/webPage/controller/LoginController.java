package com.rusarfi.webPage.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController extends BaseController {

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // nombre de la vista login.html
    }

    @GetMapping("/logout-success")
    public String logoutExitoso() {
        return "redirect:/login?logout";
    }
}