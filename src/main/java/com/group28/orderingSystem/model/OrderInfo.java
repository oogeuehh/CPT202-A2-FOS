package com.group28.orderingSystem.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Entity
public class OrderInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
    private Integer status = 1;

    //下单用户id
    private Integer userId = null;

    //下单时间
    private LocalDateTime orderTime= null;

    //结账时间
    private LocalDateTime checkoutTime= null;

    //支付方式 1微信，2支付宝
    private Integer payMethod= null;

    //实收金额
    private Double amount= 0.0;

    //备注
    private String remark= null;

    //用户名
    private String userName= null;

    //手机号
    private String phone= null;

    //地址
    private String address= null;

    //收货人
    private String consignee= null;

    private Integer score;

    private String comment;


}