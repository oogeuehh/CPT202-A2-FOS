package com.group28.orderingSystem.service;

import com.group28.orderingSystem.mapper.CartMapper;
import com.group28.orderingSystem.model.Cart;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Resource
    private CartMapper cartMapper;
    public Cart add(Cart cart) {
        /*// 判断该用户对该商品有没有加入过购物车，如果加入过，那么只要更新一下该条记录的num（+1）
        Cart dbCart = cartMapper.selectByUserIdAndGoodsId(cart.getUserId(), cart.getGoodsId());
        if (ObjectUtil.isNotEmpty(dbCart)) {
            dbCart.setNum(dbCart.getNum() + 1);
            cartMapper.updateById(dbCart);
        } else {*/
            cartMapper.insert(cart);
        return cart;
    }
    public void deleteById(Integer id) {
        cartMapper.deleteById(id);
    }
    public void updateById(Cart cart) {
        cartMapper.updateById(cart);
    }
    public void updateNumById(Cart cart) {
        cartMapper.updateNumById(cart);
    }
    public Cart selectById(Integer id) {
        return cartMapper.selectById(id);
    }

    public List<Cart> selectAll(Cart cart) {
        return cartMapper.selectAll(cart);
    }
    public List<Cart> selectByUserId(Integer userId) {
        return cartMapper.selectByUserId(userId);
    }
    public List<Cart> selectByUserIdAndGoodsId(Integer userId ,Integer goodsId) {
        return cartMapper.selectByUserIdAndGoodsId(userId,goodsId);
    }

    public void removeByUserId(Integer userId) {
        cartMapper.removeByUserId(userId);
    }
}
