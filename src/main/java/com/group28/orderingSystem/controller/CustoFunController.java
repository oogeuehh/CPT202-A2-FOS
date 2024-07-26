package com.group28.orderingSystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group28.orderingSystem.model.*;
import com.group28.orderingSystem.repository.OrderDetailRepo;
import com.group28.orderingSystem.repository.TypeRepo;
import com.group28.orderingSystem.repository.userRepo;
import com.group28.orderingSystem.service.CartService;
import com.group28.orderingSystem.service.FavoriteService;
import com.group28.orderingSystem.service.MenuService;
import com.group28.orderingSystem.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@Controller
@RequestMapping("/customer")
public class CustoFunController {
    @Autowired
    MenuService menuService;
    @Autowired
    TypeRepo typeRepo;
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    OrderDetailRepo orderDetailRepo;
    @Autowired
    private userRepo UserRepo;

    @RequestMapping("/CustomMenu") //全部查询
    public String selectAll(Model model,
                            @RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "9") int pageSize,
                            @RequestParam(required = false) String keywords,
                            @RequestParam(required = false) Integer typeId){

        PageHelper.startPage(pageNum,pageSize);
        List<Menu> list;
        if (keywords != null && !keywords.isEmpty()) {
            list = menuService.getMenuList(keywords);
        } else if (typeId != null) { // 如果 typeId 不为空，则根据 typeId 查询对应的菜单列表
            list = menuService.getMenuListByTypeId(typeId);
        } else { // 否则，查询所有菜单列表
            list = menuService.selectAllLoaded();
        }

        PageInfo<Menu> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        List<Type> typeList = typeRepo.findAll();
        model.addAttribute("typeList",typeList);
        return "profile/CustomMenu";
    }
    @RequestMapping("/foodDetail/{id}")
    public String selectForDetail(Model model,@PathVariable Integer id){
        Menu menu = menuService.selectById(id);
        model.addAttribute("menu", menu);

        return "menu/menuDetail";
    }

    @RequestMapping("/Cart")
    public String ViewCart(HttpServletRequest request){
        HttpSession session = request.getSession();

        Integer userId = (Integer) session.getAttribute("currentUserID");

        if (userId != null) {
            User currentUser = UserRepo.findById(userId).orElse(null);
            if (currentUser != null) {

                return "redirect:/cart/viewCart";
            }
        }
        return "redirect:/login";

    }

    @RequestMapping("/Collection")
    public String ViewCollection(HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("currentUserID");

        if (userId != null) {
            User currentUser = UserRepo.findById(userId).orElse(null);
            if (currentUser != null) {
//                model.addAttribute("user", currentUser);
                return "profile/collection";
            }
        }
        return "redirect:/login";

    }

    @RequestMapping("/CheckedOrder")
    public String checkOrder(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("currentUserID");

        if (userId != null) {
            User currentUser = UserRepo.findById(userId).orElse(null);
            if (currentUser != null) {
                List<Cart> cartList = cartService.selectByUserId(currentUser.getId());

                double totalPrice = cartList.stream()
                        .mapToDouble(cart -> cart.getNum() * cart.getGoodsPrice())
                        .sum();
                // 将总价传递给模板

                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("cartList", cartList);
                model.addAttribute("user", currentUser);
                return "order/checkOrder";
            }
        }
        return "redirect:/login";

    }

    @RequestMapping("/CheckedOut")
    public String CheckedOut(HttpServletRequest request,Model model,
                             @RequestParam("phone") String phone,
                             @RequestParam("address") String address,
                             @RequestParam("consignee") String consignee,
                             @RequestParam(value = "remark", required = false) String remark ,
                             @RequestParam("paymentMethod") Integer paymentMethod){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("currentUserID");

        if (userId != null) {
            User currentUser = UserRepo.findById(userId).orElse(null);
            if (currentUser != null) {
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setStatus(1);
                orderInfo.setUserId(userId);
                orderInfo.setOrderTime(LocalDateTime.now());
                orderInfo.setRemark(remark);
                orderInfo.setUserName(currentUser.getUsername());
                orderInfo.setPhone(phone);
                orderInfo.setAddress(address);
                orderInfo.setConsignee(consignee);
                orderInfo.setPayMethod(paymentMethod);

                orderService.insertSelectiveF(orderInfo);

                List<Cart> cartList = cartService.selectByUserId(currentUser.getId());
                Double price=0.0;
                for(Cart cart: cartList){
                    OrderDetail orderDetail= new OrderDetail();
                    orderDetail.setUid(userId);
                    orderDetail.setOrderId(orderInfo.getId());
                    orderDetail.setFid(cart.getGoodsId());
                    orderDetail.setFoodName(cart.getGoodsName());
                    orderDetail.setDishFlavor(cart.getDishFlavor());
                    orderDetail.setNumber(cart.getNum());
                    orderDetail.setAmount(cart.getGoodsPrice()*cart.getNum());
                    orderDetail.setImage(cart.getGoodsImg());
                    orderDetailRepo.save(orderDetail);
                    price = price + cart.getGoodsPrice() * cart.getNum();
                }
                orderInfo.setAmount(price);
                orderService.updateByPrimaryKey(orderInfo);
                orderService.updateOrderInfoAmount();
                cartService.removeByUserId(currentUser.getId());
                model.addAttribute("orderInfo", orderInfo);
                model.addAttribute("user", currentUser);
                if(orderInfo.getPayMethod().equals(1)){
                    return "order/wechatPay";
                }else{
                    return "order/alipay";
                }

            }
        }
        return "redirect:/login";

    }

    @RequestMapping("/finishPay")
    public String finishPay(HttpServletRequest request,Model model,
                            @RequestParam("id") Integer id){
        OrderInfo orderInfo =orderService.selectByPrimaryKey(id);
        orderInfo.setCheckoutTime(LocalDateTime.now());
        orderInfo.setStatus(2);
        orderService.updatePaytimeAndStatus(orderInfo);
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId(orderInfo.getId());
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orderInfo", orderInfo);
        return "order/detailForUser";
//        return "redirect:/customer/allPastOrder";
    }
    @RequestMapping("/viewDetail/{id}")
    public String viewdetail(HttpServletRequest request,Model model,
                             @PathVariable Integer id){
        OrderInfo orderInfo =orderService.selectByPrimaryKey(id);
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId(orderInfo.getId());
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orderInfo", orderInfo);
        return "order/detailForUser";
    }


    @RequestMapping("/received")
    public String receive(HttpServletRequest request,Model model,
                            @RequestParam("id") Integer id){
        OrderInfo orderInfo =orderService.selectByPrimaryKey(id);
        orderInfo.setStatus(4);
        orderService.updateStatus(orderInfo);
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId(orderInfo.getId());
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orderInfo", orderInfo);
        return "order/detailForUser";
    }

    @RequestMapping("/mark")
    public String mark(HttpServletRequest request,Model model,
                       @RequestParam("id") Integer id,
                       @RequestParam("rating") Integer score,
                       @RequestParam(value = "comment", required = false) String comment){
        OrderInfo orderInfo =orderService.selectByPrimaryKey(id);
        orderInfo.setStatus(5);
        orderInfo.setScore(score);
        orderInfo.setComment(comment);
        orderService.updateStatus(orderInfo);
        orderService.updateEvaluation(orderInfo);

        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId(orderInfo.getId());
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orderInfo", orderInfo);
        return "order/detailForUser";
    }

    @RequestMapping("/allPastOrder")
    public String mark(
            @RequestParam(value = "pageNum", defaultValue = "1", required = false)Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize,
            HttpServletRequest request,Model model){
        if(pageNum == null ||pageNum.equals("")||pageNum<0){
            pageNum = 1;
        }
        if(pageSize==null||pageSize.equals("")||pageSize<0){
            pageSize=10;
        }
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("currentUserID");

        if (userId != null) {
            User currentUser = UserRepo.findById(userId).orElse(null);
            if (currentUser != null) {
                PageHelper.startPage(pageNum,pageSize);
                List<OrderInfo> orderInfos = orderService.selectByUserId(userId);
                PageInfo<OrderInfo> pageInfo=new PageInfo<>(orderInfos);
                model.addAttribute("pageInfo",pageInfo);
                return "order/allPastOrder";
            }
        }
        return "redirect:/login";

    }

}
