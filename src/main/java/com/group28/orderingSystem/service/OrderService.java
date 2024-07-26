package com.group28.orderingSystem.service;

import com.github.pagehelper.PageInfo;
import com.group28.orderingSystem.model.OrderInfo;
import com.group28.orderingSystem.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public List<Map<String, Object>> getMonthlyOrderSummary() {
        return orderMapper.getMonthlyOrderSummary();
    }

    public int deleteByPrimaryKey(Integer id) {
        return orderMapper.deleteByPrimaryKey(id);
    }


    public Integer insertSelective(OrderInfo record) {
        return orderMapper.insertSelective(record);
    }

    public Integer insertSelectiveF(OrderInfo record) {
        return orderMapper.insertSelectiveF(record);
    }


    public OrderInfo selectByPrimaryKey(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(OrderInfo orders) {
        return orderMapper.updateByPrimaryKey(orders);
    }
    public int updateStatus(OrderInfo orders) {
        return orderMapper.updateStatus(orders);
    }

    public int updatePaytimeAndStatus(OrderInfo orders) {
        return orderMapper.updatePaytimeAndStatus(orders);
    }

    public int updateEvaluation(OrderInfo orders) {
        return orderMapper.updateEvaluation(orders);
    }


    public List<OrderInfo> getAll() {
        return orderMapper.getAll();
    }

    public List<OrderInfo> selectKeyWords(String keywords) {
        return orderMapper.selectKeyWords(keywords);
    }
    public List<OrderInfo> selectByUserId(Integer id) {
        return orderMapper.selectByUserId(id);
    }

    public void updateOrderInfoAmount() {
        orderMapper.updateOrderInfoAmount();
    }
}
