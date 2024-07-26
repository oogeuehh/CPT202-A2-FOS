package com.group28.orderingSystem.controller;

import com.group28.orderingSystem.model.Favorite;
import com.group28.orderingSystem.service.FavoriteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/user")
public class favoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/Favorite")
    public String showFavorites(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("currentUserID");
        if (userId != null) {
            List<Favorite> favorites = (List<Favorite>) favoriteService.getFavoriteById(userId);
            model.addAttribute("favorites", favorites);
            // 这里没有导向
            return "profile/Favorite";
        } else {
            return "redirect:/login";
        }
    }
}
