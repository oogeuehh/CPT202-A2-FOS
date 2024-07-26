package com.group28.orderingSystem.service;

//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group28.orderingSystem.model.Menu;
import com.group28.orderingSystem.mapper.MenuMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

@Service//注入容器，注入springboot
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    public Menu insertMenuItem(Menu menu) {
        menuMapper.insert(menu);
        return menu;
    }
    public void updateMenu(Menu menu){
        //在service里面传到mapper
        menuMapper.update(menu);

    }

    public void deleteUser(Integer id) {
        menuMapper.delete(id);
    }

    public void batchDeleteUser(List<Integer> ids) {
        for (Integer id : ids){
            menuMapper.delete(id);
        }
    }

    public List<Menu> selectAll() {
        return menuMapper.selectAll();
    }

//    public PageInfo<Menu> selectAll(int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<Menu> menuList = menuMapper.selectAll();
//        return new PageInfo<>(menuList);
//    }

    public List<Menu> selectAllLoaded() {
        List<Menu> menuList = menuMapper.selectAllLoaded();
        return menuList;
    }
    public Menu selectById(Integer id) {
        return menuMapper.selectById(id);
    }


    public List<Menu> getMenuList(String keywords) {
        return menuMapper.selectMenuList(keywords);
    }

    public List<Menu> getMenuListByTypeId(Integer typeId) {
        return menuMapper.findByTypeId(typeId);
    }
}
