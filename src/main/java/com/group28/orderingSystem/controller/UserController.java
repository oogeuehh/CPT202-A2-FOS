package com.group28.orderingSystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group28.orderingSystem.model.Menu;
import com.group28.orderingSystem.model.User;
import com.group28.orderingSystem.repository.userRepo;
import com.group28.orderingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller/* Springboot annotation: to tell Springboot to add feathers to this class
                      so that this class can listen and respond to HTTP Request*/
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private userRepo UserRepo;

    @GetMapping("/customerList")
    public String getAllCustomers(Model model,
                                  @RequestParam(value = "pageNum", defaultValue = "1", required = false)Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize,
                                  @RequestParam(required = false) String keywords) {
        if(pageNum == null ||pageNum.equals("")||pageNum<0){
            pageNum = 1;
        }
        if(pageSize==null||pageSize.equals("")||pageSize<0){
            pageSize=10;
        }

        PageHelper.startPage(pageNum,pageSize);
        List<User> list;
        if (keywords != null && !keywords.isEmpty()) {
            list = userService.selectByKeywords(keywords);
        }  else {
            list = userService.selectAll();
        }
        PageInfo<User> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        return "user/customerList";
    }

    @GetMapping("/deleteUser/{id}")
    public String delete(@PathVariable Integer id){
        userService.deleteUser(id);
        return "redirect:/user/customerList";
    }

    @RequestMapping("/addUser")
    public String addUser(User user){
        return "user/addUser";
    }

    @PostMapping("/insertUser")
    public String insertUser(User user){
//        userService.insertUser(user);
        UserRepo.save(user);
        return "redirect:/user/customerList";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUser(Model model, @PathVariable Integer id){
        User user = userService.selectById(id);
        model.addAttribute("user", user);
        return "user/updateUser";
    }

//    @PostMapping("/updateNewUser")
//    public String updateUser(User user){
//        userService.updateMenu(user);
//        return "redirect:/user/customerList";
//    }
    @PostMapping("/updateNewUser")
    public String updateUser(@RequestParam("id") Integer id,
                             @RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("phone") String phone,
                             @RequestParam("address") String address,Model model){
            User user = UserRepo.findById(id).orElse(null);
            if (user != null) {
                user.setUsername(username);
                user.setEmail(email);
                user.setPhone(phone);
                user.setAddress(address);
                UserRepo.save(user); // 保存用户信息
            }
        return "redirect:/user/customerList";
    }


}
