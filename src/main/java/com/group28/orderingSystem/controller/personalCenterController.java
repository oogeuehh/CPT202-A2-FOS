package com.group28.orderingSystem.controller;

import com.group28.orderingSystem.model.User;
import com.group28.orderingSystem.repository.userRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class personalCenterController {
    @Autowired
    private userRepo UserRepo;

    @GetMapping("/profile")
    public String userProfile(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("currentUserID");
        if (userId != null) {
            User currentUser = UserRepo.findById(userId).orElse(null);
            if (currentUser != null) {
                model.addAttribute("user", currentUser);
                return "profile/personalCenter";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("currentUserID");
        if (userId != null) {
            User currentUser = UserRepo.findById(userId).orElse(null);
            if (currentUser != null) {
                model.addAttribute("user", currentUser);
                return "profile/editProfile"; // 返回编辑页面
            }

        }
        return "redirect:/login";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(@RequestParam("id") Integer id,
                              @RequestParam("username") String username,
                              @RequestParam("email") String email,
                              @RequestParam("phone") String phone,
                              @RequestParam("address") String address,Model model) {
        User user = UserRepo.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(username);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(address);
            UserRepo.save(user); // 保存用户信息
        }
        return "redirect:/user/profile"; // 保存成功后重定向到个人中心页面
    }




}
