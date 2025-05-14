package org.example.openbid.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/sell")
    public String sell() {
        return "sell";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // logs out the user
        return "redirect:/";
    }

}