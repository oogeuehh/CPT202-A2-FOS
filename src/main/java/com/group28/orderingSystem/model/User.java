package com.group28.orderingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String image;
    private String address;
    private LocalDateTime registrationDate;
    private LocalDateTime lastLogin;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private AccountStatus accountStatus; // 0 -- active; 1 -- closed
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;  // 0 -- manager; 1 -- customer
    //这个后续需要跟OrderHistory的表连一下
//    private String orderHistory;
//
//    public User() {
//
//    }
//
//    public User(Integer id, String username, String password, String email,String phone, String image) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.phone = phone;
//        this.image = image;
//    }
//    public User(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
}
