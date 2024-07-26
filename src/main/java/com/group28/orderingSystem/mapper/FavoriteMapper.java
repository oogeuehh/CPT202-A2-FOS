package com.group28.orderingSystem.mapper;

import com.group28.orderingSystem.model.Favorite;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    // 插入新的收藏记录
    @Insert("INSERT INTO `favorites` (user_id, menu_id, created_at, updated_at, is_active) " +
            "VALUES(#{user.id}, #{menu.id}, #{createdAt}, #{updatedAt}, #{isActive})")
    @Options(useGeneratedKeys = true, keyProperty = "favoriteId")
    int insert(Favorite favorite);

    // 根据ID查找收藏记录
    @Select("SELECT * FROM `favorites` WHERE favorite_id = #{id}")
    Favorite findById(@Param("id") Integer id);

    // 获取某个用户的所有收藏记录
    @Select("SELECT * FROM `favorites` WHERE user_id = #{userId} AND is_active = true")
    List<Favorite> findAllByUserId(@Param("userId") Integer userId);

    // 更新收藏记录的状态
    @Update("UPDATE `favorites` SET is_active = #{isActive}, updated_at = NOW() WHERE favorite_id = #{id}")
    int updateIsActive(@Param("id") Integer id, @Param("isActive") Boolean isActive);

    // 删除收藏记录（逻辑删除或物理删除）
    @Delete("DELETE FROM `favorites` WHERE favorite_id = #{id}")
    int deleteById(@Param("id") Integer id);

    // 逻辑删除：更新收藏记录的状态为非激活
    @Update("UPDATE `favorites` SET is_active = false, updated_at = NOW() WHERE favorite_id = #{id}")
    int deactivateById(@Param("id") Integer id);
}
