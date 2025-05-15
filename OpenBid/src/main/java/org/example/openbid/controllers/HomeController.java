package org.example.openbid.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.openbid.domain.AppUser;
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
    public String sellPage(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        return "sell";
    }


    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/panel")
    public String panelPage(HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user != null && "admin".equals(user.getUsername())) {
            return "/panel";
        }
        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // logs out the user
        return "redirect:/";
    }

}