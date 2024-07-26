package com.group28.orderingSystem.service;

import com.group28.orderingSystem.mapper.FavoriteMapper;
import com.group28.orderingSystem.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteMapper favoriteMapper;

    @Autowired
    public FavoriteService(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    // 添加收藏
    public int addFavorite(Favorite favorite) {
        return favoriteMapper.insert(favorite);
    }

    // 根据收藏ID删除收藏
    public int removeFavorite(Integer favoriteId) {
        return favoriteMapper.deleteById(favoriteId);
    }

    // 获取指定用户的所有有效收藏
    public List<Favorite> getUserFavorites(Integer userId) {
        return favoriteMapper.findAllByUserId(userId);
    }

    // 根据菜单ID禁用所有相关的收藏项
    public int deactivateFavoriteByMenuId(Integer menuId) {
        return favoriteMapper.deactivateById(menuId);
    }

    // 根据收藏ID更新收藏的激活状态
    public int updateFavoriteActiveStatus(Integer favoriteId, boolean isActive) {
        return favoriteMapper.updateIsActive(favoriteId, isActive);
    }

    // 根据ID查找收藏记录
    public Favorite getFavoriteById(Integer favoriteId) {
        return favoriteMapper.findById(favoriteId);
    }

}
