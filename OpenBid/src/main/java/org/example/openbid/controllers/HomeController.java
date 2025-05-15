package org.example.openbid.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.openbid.domain.AppUser;
import org.example.openbid.domain.Item;
import org.example.openbid.repositories.ItemRepository;
import org.example.openbid.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ItemRepository itemRepository;

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
    @PostMapping("/sell")
    public String postItem(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("startingBid") Double startingBid,
            @RequestParam("image") MultipartFile imageFile,
            HttpSession session) throws IOException {

        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setStartingBid(startingBid);

        AppUser user = (AppUser) session.getAttribute("user");
        if (user != null) {
            item.setOwner(user);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            item.setImage(imageFile.getBytes());
        }

        itemRepository.save(item);
        return "redirect:/";
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