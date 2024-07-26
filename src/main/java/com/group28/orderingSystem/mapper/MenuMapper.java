package com.group28.orderingSystem.mapper;

//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group28.orderingSystem.model.Menu;
import com.group28.orderingSystem.model.OrderInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {
    @Insert("INSERT INTO `menu` (name, type_id, taste, small_price,medium_price, large_price, descr,is_loaded) " +
            "VALUES (#{name}, #{type.id}, #{taste}, #{smallPrice}, #{mediumPrice}, #{largePrice},#{descr}, #{isLoaded})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Menu menu);

    @Update("UPDATE `menu` SET name=#{name}, type_id=#{type.id}, taste=#{taste}, small_price=#{smallPrice}, " +
            "medium_price=#{mediumPrice},large_price=#{largePrice} ,descr=#{descr}, stock=#{stock}, image=#{image}, is_loaded=#{isLoaded} " +
            "WHERE id=#{id}")
    void update(Menu menu);


    @Delete("delete from `menu` where id=#{id}")
    void delete(Integer id);

    @Select("select * from `menu` order by id desc") //排序查询，倒叙
    List<Menu> selectAll();

    @Select("select * from `menu` where is_loaded = 1 order by id desc")
    List<Menu> selectAllLoaded();

    @Select("select * from `menu` where id =#{id} order by id desc")
    Menu selectById(Integer id);

    @Select("select * from `menu` where name like CONCAT('%',#{keywords},'%') " +
            "or descr like CONCAT('%',#{keywords},'%') ")
    List<Menu> selectMenuList(String keywords);
    @Select("SELECT * FROM menu WHERE type_id = #{typeId}")
    List<Menu> findByTypeId(Integer typeId);
}
