package com.group28.orderingSystem.mapper;

import com.group28.orderingSystem.model.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    @Insert("INSERT INTO `cart` (goods_id, user_id, num, dish_flavor, goods_name, goods_img, goods_price) " +
            "VALUES (#{goodsId}, #{userId}, #{num}, #{dishFlavor}, #{goodsName}, #{goodsImg}, #{goodsPrice})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Cart cart);
    //int insert(Cart cart);

    @Delete("delete from `cart` where id=#{id}")
    void deleteById(Integer id);
   // int deleteById(Integer id);

    @Update("UPDATE `cart` SET goods_id=#{goodsId}, user_id=#{userId}, num=#{num}, " +
            "goods_name=#{goodsName}, goods_img=#{goodsImg}, dish_flavor=#{dishFlavor}, goods_price=#{goodsPrice} " +
            "WHERE id=#{id}")
    void updateById(Cart cart);
    //int updateById(Cart cart);

    @Update("UPDATE `cart` SET num=#{num} " +
            "WHERE id=#{id}")
    void updateNumById(Cart cart);

    @Delete("DELETE FROM `cart` WHERE user_id = #{userId}")
    void removeByUserId(Integer userId);


    @Select("select * from `cart` where id =#{id} order by id desc")
    Cart selectById(Integer id);

    @Select("select * from `cart` order by id desc") //排序查询，倒叙
    List<Cart> selectAll(Cart cart);

    @Select("select * from cart where user_id = #{userId} and goods_id = #{goodsId}")
    List<Cart> selectByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);
    @Select("select * from cart where user_id = #{userId}")
    List<Cart> selectByUserId(@Param("userId") Integer userId);

}
