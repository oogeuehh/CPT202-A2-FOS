package com.group28.orderingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
//购物车单项
@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String goodsName =null;
    private Integer userId=null;
    private Integer goodsId=null;

    private String dishFlavor = null; // 设置为可空，初始化为null
    private Integer num = 0; // 设置为可空，初始化为null
    private Double goodsPrice = null; // 设置为可空，初始化为null

    private String goodsImg=null;

}