package com.group28.orderingSystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group28.orderingSystem.model.Menu;
import com.group28.orderingSystem.model.Type;
import com.group28.orderingSystem.model.User;
import com.group28.orderingSystem.repository.TypeRepo;
import com.group28.orderingSystem.repository.userRepo;
import com.group28.orderingSystem.service.MenuService;
import com.group28.orderingSystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class logController {
    @Autowired
    private userRepo UserRepo;
    @Autowired
    MenuService menuService;
    @Autowired
    TypeRepo typeRepo;
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String showLoginFormSelect(Model model) {
        model.addAttribute("user", new User());
        return "cusOradmin";
    }

    @GetMapping("/login/admin")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "loginAdmin";
    }
    @GetMapping("/login/customer")
    public String showLoginFormForcustomer(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    @GetMapping("/homepage")
    public String showDashboard(Model model) {
//        model.addAttribute("user", new User());
        return "dashboard";
    }

    @GetMapping("/customer/homepage")
    public String showhomepage(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("currentUserID");
        if (userId != null) {
            User currentUser = UserRepo.findById(userId).orElse(null);
            if (currentUser != null) {
                model.addAttribute("user", currentUser);
                return "customer";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model, HttpSession session) {
        if ("admin".equals(user.getUsername())&&"password".equals(user.getPassword())) {
            return "redirect:/homepage";
        } else if ("admin".equals(user.getUsername())&&(!"password".equals(user.getPassword()))) {
            return "loginAdmin";
        } else {
            User existingUser = UserRepo.findByUsername(user.getUsername());
//            User existingUser = UserRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            if (existingUser != null  && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {

                session.setAttribute("currentUser", existingUser);
                session.setAttribute("currentUserID", existingUser.getId());

//                return "redirect:/user/profile";
                return "redirect:/customer/homepage";
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        }

    }


    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("newUser",new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerAdd(@ModelAttribute User newUser,Model m){
//        if(newUser.getUsername().equals())

        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        UserRepo.save(newUser);
        return "login";
    }

    @GetMapping("/passwordreset")
    public String showPasswordReset(Model model) {
        model.addAttribute("user", new User());
        return "passwordreset";
    }

    @PostMapping("/passwordreset")
    public String changePassword(@ModelAttribute("user") User user, @RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("phone") String phone,
                                 @RequestParam("newPassword") String newPassword, @RequestParam("confirmNewPassword") String confirmNewPassword,
                                 Model model) {
        User existingUser = UserRepo.findByUsernameAndEmailAndPhone(user.getUsername(), user.getEmail(), user.getPhone());
        if (existingUser == null) {
            model.addAttribute("error", "No user founded");
            return "passwordreset";
        }
        Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*\\d)");
        Matcher matcher = pattern.matcher(newPassword);

        if (newPassword.length()<=6||!matcher.find()) {
            model.addAttribute("error", "Password length must be greater than 6 characters, consists of numbers and letters, please re-enter!");
            return "passwordreset";
        }

        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "New password and confirm password do not match");
            return "passwordreset";
        }

        existingUser.setPassword(newPassword);
        UserRepo.save(existingUser);

        return "redirect:/login";
    }


    @RequestMapping("/checkUsernameAvailability")
    @ResponseBody
    public boolean checkUsernameAvailability(@RequestParam("tel_num") String telNum) {

        return !userService.isUsernameAvailable(telNum);
    }

    @RequestMapping("/checkEmailAvailability")
    @ResponseBody
    public boolean checkEmailAvailability(@RequestParam("tel_email") String email) {

        return !userService.isEmailAvailable(email);
    }


}

