package com.group28.orderingSystem.mapper;

import com.group28.orderingSystem.model.Menu;
import com.group28.orderingSystem.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.*;
import java.util.List;
@Mapper
public interface UserMapper {
    @Select("select * from `user` order by id desc")
    List<User> selectAll();

    @Delete("delete from `user` where id=#{id}")
    void delete(Integer id);

    @Select("select * from `user` where username like CONCAT('%',#{keywords},'%') " +
            "or id like CONCAT('%',#{keywords},'%') ")
    List<User> selectByKeywords(String keywords);


    @Select("select * from `user` where id =#{id} order by id desc")
    User selectById(Integer id);

    @Update("UPDATE `user` SET username=#{username}, address=#{address}, phone=#{phone}, email=#{email} " +
            "WHERE id=#{id}")
    void update(User user);

}
