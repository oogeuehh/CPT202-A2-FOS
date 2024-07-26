package com.group28.orderingSystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


/**
 * 订单明细
 */
@Data
@Entity
public class OrderDetail{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //名称
    private Integer uid;

    //订单id
    private Integer orderId;


    //菜品id
    private Integer fid;
    private String foodName;

    //口味
    private String dishFlavor;


    //数量
    private Integer number;

    //金额
    private Double amount;

    private String image;

//    //外键
//    @TableField(exist = false)
//    @ManyToOne
//    @JoinColumn(name = "uid", insertable = false, updatable = false)
//    private User user;
//
//    @TableField(exist = false)
//    @ManyToOne
//    @JoinColumn(name = "fid", insertable = false, updatable = false)
//    private Menu menu;
//
//    @TableField(exist = false)
//    @ManyToOne
//    @JoinColumn(name = "orderId", insertable = false, updatable = false)
//    private OrderInfo order;


}
