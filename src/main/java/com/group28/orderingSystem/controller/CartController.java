package com.group28.orderingSystem.controller;

import com.group28.orderingSystem.common.Result;
import com.group28.orderingSystem.model.Cart;
import com.group28.orderingSystem.model.Flavor;
import com.group28.orderingSystem.model.Menu;
import com.group28.orderingSystem.repository.FlavorRepo;
import com.group28.orderingSystem.repository.userRepo;
import com.group28.orderingSystem.service.CartService;
import com.group28.orderingSystem.service.MenuService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private userRepo UserRepo;
    @Autowired
    private CartService cartService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private FlavorRepo flavorRepo;

    @RequestMapping("/add/{id}")
    public String add(HttpServletRequest request, Model model,
                      @PathVariable Integer id){
        Menu menu = menuService.selectById(id);
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("currentUserID");

        List<Flavor> flavors = flavorRepo.findByMenuId(id);

        model.addAttribute("UserId",userId );
        model.addAttribute("menu",menu );
        model.addAttribute("flavorList",flavors );

        return "cart/addCartItem";
    }

    @PostMapping("/addCartItem")
    public String add(@RequestParam("goodsName") String goodsName,
                      @RequestParam("userId") Integer userId,
                      @RequestParam("goodsId") Integer goodsId,
                      @RequestParam("goodsImg") String goodsImg,
                      @RequestParam("size") Double size,
                      @RequestParam(value = "spiciness", required = false) String spiciness,
                      @RequestParam(value = "cilantro", required = false) String cilantro,
                      @RequestParam(value = "greenOnion", required = false) String greenOnion,
                      @RequestParam(value = "garlic", required = false) String garlic){
        /********************************************/

        StringBuilder result = new StringBuilder();

        if (spiciness != null) {
            result.append("Spiciness: ");
            result.append(spiciness);
        }

        if ("Yes".equals(cilantro)) {
            result.append("   Cilantro: ");
            result.append("Yes, ");
        } else if ("No".equals(cilantro)) {
            result.append("   Cilantro: ");
            result.append("No, ");
        } else {
            result.append(" ");
        }
        if ("Yes".equals(greenOnion)) {
            result.append("  Green Onion: ");
            result.append("Yes, ");
        } else if ("No".equals(greenOnion)) {
            result.append("  Green Onion: ");
            result.append("No, ");
        } else {
            result.append(" ");
        }

        if ("Yes".equals(garlic)) {
            result.append("  Garlic: ");
            result.append("Yes");
        } else if ("No".equals(garlic)) {
            result.append("  Garlic: ");
            result.append("No");
        } else {
            result.append(" ");
        }

        String flavorResult = result.toString();



        /*******************************************/

        List<Cart> cartServiceOne = cartService.selectByUserIdAndGoodsId(userId, goodsId);
        Optional<Cart> optionalCart = cartServiceOne.stream()
                .filter(cart -> cart.getGoodsPrice().equals(size))
                .filter(cart -> cart.getDishFlavor().equals(flavorResult))
                .findFirst();
        if(optionalCart.isPresent()){
        //如果已经存在，就在原来数量基础上加一
            Cart cart = optionalCart.get();
            Integer number = cart.getNum();
            cart.setNum(number + 1);
//            cart.setDishFlavor(cart.getDishFlavor()+"; "+flavorResult);
            cartService.updateById(cart);
        }else{
            //如果不存在，则添加到购物车，数量默认就是一
            Cart cart1 = new Cart();
            cart1.setGoodsName(goodsName);
            cart1.setUserId(userId);
            cart1.setGoodsId(goodsId);
            cart1.setDishFlavor(flavorResult);
            cart1.setNum(1);
            cart1.setGoodsPrice(size);
            cart1.setGoodsImg(goodsImg);
            cartService.add(cart1);
        }

        return "redirect:/cart/viewCart";
    }



    @RequestMapping("/viewCart")
    public String viewAllCart(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("currentUserID");
        List<Cart> cartList = cartService.selectByUserId(userId);
        model.addAttribute("cartList", cartList);
        Double price=0.0;
        DecimalFormat df = new DecimalFormat("#.00");
        for(Cart cart: cartList){
            price = price + cart.getGoodsPrice() * cart.getNum();
        }
        String formattedPrice = df.format(price);
        model.addAttribute("Price", price);
        return "cart/Cart";
    }

    @RequestMapping("/cleanCart")
    public String cleanAllCart(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("currentUserID");
        cartService.removeByUserId(userId);

        return "redirect:/cart/viewCart";
    }

    @RequestMapping("/deletedCartItem/{id}")
    public String deletedCartItem(HttpServletRequest request,Model model,@PathVariable Integer id){

        cartService.deleteById(id);
        return "redirect:/cart/viewCart";
    }

    @RequestMapping("/changeNumberSub/{id}")
    public String changeNumberCartItemSub(HttpServletRequest request,Model model,@PathVariable Integer id){
        Cart cart = cartService.selectById(id);
        cart.setNum(cart.getNum()-1);
        cartService.updateNumById(cart);
        return "redirect:/cart/viewCart";
    }
    @RequestMapping("/changeNumberAdd/{id}")
    public String changeNumberCartItemAdd(HttpServletRequest request,Model model,@PathVariable Integer id){
        Cart cart = cartService.selectById(id);
        cart.setNum(cart.getNum()+1);
        cartService.updateNumById(cart);
        return "redirect:/cart/viewCart";
    }



}
