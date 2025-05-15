package org.example.openbid.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.openbid.domain.AppUser;
import org.example.openbid.domain.Item;
import org.example.openbid.repositories.AppUserRepository;
import org.example.openbid.repositories.ItemRepository;
import org.example.openbid.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        LocalDateTime now = LocalDateTime.now();
        items.forEach(item -> {
            if (item.isActive() && item.getEndTime() != null && item.getEndTime().isBefore(now)) {
                item.setActive(false);
                itemRepository.save(item);
            }
            if (item.getWinner() != 0) {
                try {
                    int winnerId = item.getWinner();
                    AppUser winner = appUserRepository.findById(winnerId)
                            .orElse(null);
                    model.addAttribute("winnerUsername", winner != null ? winner.getUsername() : "Unknown User");
                } catch (NumberFormatException e) {
                    model.addAttribute("winnerUsername", "Invalid User ID");
                }
            } else {
                model.addAttribute("winnerUsername", "No Bidder");
            }
        });
        model.addAttribute("items", items);
        return "home";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item != null && item.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Adjust MIME type as needed
            return new ResponseEntity<>(item.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
            @RequestParam("endTime") LocalDateTime endTime,
            HttpSession session) throws IOException {

        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setStartingBid(startingBid);
        item.setEndTime(endTime);
        item.setActive(true);
        item.setApproved(false);

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
    public String panelPage(HttpSession session, Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        LocalDateTime now = LocalDateTime.now();
        items.forEach(item -> {
            if (item.isActive() && item.getEndTime() != null && item.getEndTime().isBefore(now)) {
                item.setActive(false);
                itemRepository.save(item);
            }
        });
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