package com.group28.orderingSystem.controller;

import com.group28.orderingSystem.service.OrderService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@ResponseBody
@RestController
@RequestMapping("/list")
public class statementController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/Summary")
    public List<Map<String, Object>> getMonthlyOrderSummary() {
        return orderService.getMonthlyOrderSummary();
    }
}
