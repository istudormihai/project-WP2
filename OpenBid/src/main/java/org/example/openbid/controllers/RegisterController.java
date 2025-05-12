package org.example.openbid.controllers;

import org.example.openbid.domain.AppUser;
import org.example.openbid.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") AppUser user, Model model) {
        if (appUserService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already registered.");
            return "register";
        }
        appUserService.registerUser(user);
        return "redirect:/login";
    }
}
