
package com.group28.orderingSystem.mapper;

import com.group28.orderingSystem.model.OrderInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    @Select("select * from order_info where id = #{id}")
    OrderInfo selectByPrimaryKey(Integer id);

    @Select("SELECT " +
            "    DATE_FORMAT(oi.order_time, '%Y-%m') AS month, " +
            "    COUNT(oi.id) AS orderCount, " +
            "    SUM(od.amount) AS totalAmount " +
            "FROM " +
            "    order_info oi " +
            "    JOIN order_detail od ON oi.id = od.order_id " +
            "GROUP BY " +
            "    DATE_FORMAT(oi.order_time, '%Y-%m');")
    List<Map<String, Object>> getMonthlyOrderSummary();
    @Select("SELECT SUM(total_money) FROM order_info")
    Double getTotalAmount();

    // 查询本月订单总数量
    @Select("SELECT COUNT(*) FROM order_info WHERE YEAR(order_time) = YEAR(CURDATE()) AND MONTH(order_time) = MONTH(CURDATE())")
    Integer getMonthlyOrderCount();

    @Delete("delete from order_info where id = #{id}")
    int deleteByPrimaryKey(Integer id);

    @Insert("INSERT INTO order_info (status, user_id, order_time, pay_method, amount, remark,user_name,phone,address,consignee) " +
            "VALUES (#{status}, #{userId}, #{orderTime}, #{payMethod}, #{amount}, #{remark}, #{userName}, #{phone},#{address},#{consignee})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertSelective(OrderInfo record);

    @Insert("INSERT INTO order_info (status, user_id, order_time, pay_method, remark,user_name,phone,address,consignee) " +
            "VALUES (#{status}, #{userId}, #{orderTime}, #{payMethod},  #{remark}, #{userName}, #{phone},#{address},#{consignee})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertSelectiveF(OrderInfo record);//首先创建订单的时候除此之外的属性为null，后面逐步update上

    @Update("UPDATE order_info " +
            "SET user_name = #{userName},address = #{address}, phone = #{phone}  " +

            "WHERE id = #{id}")
    int updateByPrimaryKey(OrderInfo order);

    @Update("UPDATE order_info " +
            "SET status = #{status} " +
            "WHERE id = #{id}")
    int updateStatus(OrderInfo order);

    @Update("UPDATE order_info " +
            "SET checkout_time = #{checkoutTime} ,status = #{status} " +
            "WHERE id = #{id}")
    int updatePaytimeAndStatus(OrderInfo order);

    @Update("UPDATE order_info " +
            "SET score = #{score}, comment = #{comment} " +
            "WHERE id = #{id}")
    int updateEvaluation(OrderInfo order);

    @Select("select * from order_info ORDER BY id DESC")
    List<OrderInfo> getAll();

    @Select("select * from order_info where user_id = #{id} ORDER BY id DESC")
    List<OrderInfo> selectByUserId(Integer id);
    @Select("select * from order_info where user_name like CONCAT('%',#{keywords},'%') " +
            "or phone like CONCAT('%',#{keywords},'%') or comment like CONCAT('%',#{keywords},'%')")
    List<OrderInfo> selectKeyWords(String keywords);

    @Update("UPDATE order_info oi " +
            "LEFT JOIN ( " +
            "    SELECT order_id, SUM(amount * number) AS total_amount " +
            "    FROM order_detail " +
            "    GROUP BY order_id " +
            ") od ON oi.id = od.order_id " +
            "SET oi.amount = od.total_amount")
    void updateOrderInfoAmount();
}
