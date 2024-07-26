package com.group28.orderingSystem.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group28.orderingSystem.mapper.MenuMapper;
import com.group28.orderingSystem.mapper.UserMapper;
import com.group28.orderingSystem.model.Menu;
import com.group28.orderingSystem.model.User;
import com.group28.orderingSystem.repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private userRepo userRepo;

    public List<User> selectAll() {
        return userMapper.selectAll();
    }
    public void deleteUser(Integer id) {
        userMapper.delete(id);
    }

    public List<User> selectByKeywords(String keywords) {
        return userMapper.selectByKeywords(keywords);
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public void updateMenu(User user){
        userMapper.update(user);
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepo.existsByUsername(username);
    }

    public boolean isEmailAvailable(String email) {
        return !userRepo.existsByEmail(email);
    }

}

