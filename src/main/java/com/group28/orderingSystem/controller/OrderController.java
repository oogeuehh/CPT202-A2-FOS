package com.group28.orderingSystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group28.orderingSystem.mapper.OrderMapper;
import com.group28.orderingSystem.model.Menu;
import com.group28.orderingSystem.model.OrderDetail;
import com.group28.orderingSystem.model.OrderInfo;
import com.group28.orderingSystem.model.User;
import com.group28.orderingSystem.repository.OrderDetailRepo;
import com.group28.orderingSystem.repository.userRepo;
import com.group28.orderingSystem.service.MenuService;
import com.group28.orderingSystem.service.OrderService;
import com.group28.orderingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Order")
public class OrderController{
    @Autowired
    private OrderService orderService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;
    @Autowired
    private userRepo userRepo;

    @Autowired
    OrderDetailRepo orderDetailRepo;

    @GetMapping("monthlySummary")
    public List<Map<String, Object>> getMonthlyOrderSummary() {
        System.out.println(orderService.getMonthlyOrderSummary());
        return orderService.getMonthlyOrderSummary();
    }

    @GetMapping("Summary")
    public List<Map<String, Object>> getMonthlySummary() {
        System.out.println(orderService.getMonthlyOrderSummary());
        return orderService.getMonthlyOrderSummary();
    }

    @GetMapping("/all")
    public String all(@RequestParam(value = "pageNum", defaultValue = "1", required = false)Integer pageNum,
                      @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize,
                      Model model,
                      OrderInfo order) {
        if(pageNum == null ||pageNum.equals("")||pageNum<0){
            pageNum = 1;
        }
        if(pageSize==null||pageSize.equals("")||pageSize<0){
            pageSize=10;
        }
        PageHelper.startPage(pageNum,pageSize);

        PageInfo<OrderInfo> pageInfo=new PageInfo<>(orderService.getAll());


        model.addAttribute("pageInfo",pageInfo);
        return "order/allOrder";
    }


@GetMapping("/keywords")
public String getKeyWords(Model model,
                          @RequestParam(value = "pageNum", defaultValue = "1", required = false)Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize,
                          @RequestParam(required = false) String keywords) {
    if(pageNum == null ||pageNum.equals("")||pageNum<0){
        pageNum = 1;
    }
    if(pageSize==null||pageSize.equals("")||pageSize<0){
        pageSize=10;
    }
    PageHelper.startPage(pageNum,pageSize);
    List<OrderInfo> list = orderService.selectKeyWords(keywords);
    PageInfo<OrderInfo> pageInfo = new PageInfo<>(list);
    model.addAttribute("pageInfo", pageInfo);
    return "order/allOrder";
}

    @GetMapping("/getOne")
    public String getOne(Integer id){
        OrderInfo orderInfo = orderService.selectByPrimaryKey(id);
        return orderInfo.toString();
    }

    @RequestMapping(value = "delete/{orderId}")
    public ModelAndView delete(@PathVariable Integer orderId) {
        orderService.deleteByPrimaryKey(orderId);
        ModelAndView mav = new ModelAndView("redirect:/Order/all");
        return mav;
    }

    @RequestMapping("addOrder")
    public ModelAndView AddOrder(OrderInfo funOrderInfo) {
//        funOrder.setAddTime(new Date());
        orderService.insertSelective(funOrderInfo);
        ModelAndView mav = new ModelAndView("redirect:/Order/all");
        return mav;
    }

    @RequestMapping("add")
    public ModelAndView Add1() {
        return new ModelAndView("/order/add");
    }

    @RequestMapping("/edit/{id}")
    public String edit(Model model,@PathVariable Integer id){
        OrderInfo orderInfo = orderService.selectByPrimaryKey(id);
        List<Menu> foodList = menuService.selectAll();
        List<User> userList = userRepo.findAll();
        model.addAttribute("userList",userList);
        model.addAttribute("foodList",foodList);
        model.addAttribute("orderInfo",orderInfo);
        return "order/edit";
    }


    @RequestMapping("/detail/{id}")
    public String Detail(@PathVariable Integer id ,Model model){
        OrderInfo orderInfo = orderService.selectByPrimaryKey(id);
        List<OrderDetail> orderDetails = orderDetailRepo.findByOrderId(orderInfo.getId());
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("orderDetails", orderDetails);
        return "order/Detail";
    }


    @RequestMapping("updateOrders")
    public ModelAndView updateOrder(@RequestParam("id") Integer id,
                                    @RequestParam("username") String username,
                                    @RequestParam("address") String address,
                                    @RequestParam("phone") String phone,Model model) {

        OrderInfo orderinfo = orderService.selectByPrimaryKey(id);
        orderinfo.setAddress(address);
        orderinfo.setUserName(username);
        orderinfo.setPhone(phone);
        orderService.updateByPrimaryKey(orderinfo);
        ModelAndView mav = new ModelAndView("redirect:/Order/all");
        return mav;
    }



    @RequestMapping("updateStatus")
    public ModelAndView updateOrder(@RequestParam("id") Integer id) {

        System.out.println("upadate");
        OrderInfo orderinfo = orderService.selectByPrimaryKey(id);
        orderinfo.setStatus(3);
        orderService.updateStatus(orderinfo);
//        System.out.println(orderinfo.getStatus());
//        return ("redirect:/Order/all");
        ModelAndView mav = new ModelAndView("redirect:/Order/all");
        return mav;
    }

}