package com.group28.orderingSystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group28.orderingSystem.model.Menu;
import com.group28.orderingSystem.model.Type;
import com.group28.orderingSystem.model.User;
import com.group28.orderingSystem.repository.TypeRepo;

import com.group28.orderingSystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeRepo typeRepository;
    @Autowired
    MenuService menuService;


    @RequestMapping("/typeList") //全部+查询
    public String selectAll(Model model,
                            @RequestParam(value = "pageNum", defaultValue = "1", required = false)Integer pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize,
                            @RequestParam(required = false) String keywords){

        if(pageNum == null ||pageNum.equals("")||pageNum<0){
            pageNum = 1;
        }
        if(pageSize==null||pageSize.equals("")||pageSize<0){
            pageSize=10;
        }

        PageHelper.startPage(pageNum,pageSize);
        List<Type> list;

        if (keywords != null && !keywords.isEmpty()) {
            list = typeRepository.findByTypeNameContaining(keywords);
        }  else {
            list = typeRepository.findAll();
        }
        PageInfo<Type> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        return "type/typeList";
    }

    @RequestMapping("/addType")
    public String addType(){
        return "type/addType";
    }

    @RequestMapping("/inserttype")
    public String addType(Type type){
        typeRepository.save(type);
        return "redirect:/type/typeList";
    }

    @GetMapping("/deleteType/{id}")
    public String delete(@PathVariable Integer id){
        Type type = typeRepository.findById(id).orElse(null);
        if (type != null) {
            typeRepository.delete(type);
        }
        return "redirect:/type/typeList";
    }

    @GetMapping("/updateType/{id}")
    public String updateUser(Model model, @PathVariable Integer id){
        Type type = typeRepository.findById(id).orElse(null);
        model.addAttribute("type", type);
        return "type/updateType";
    }
    @PostMapping("/updateNewType")
    public String updateUser(@RequestParam("id") Integer id,
                             @RequestParam("typeName") String typeName,
                             @RequestParam("descr") String descr,
                             Model model){
        Type type = typeRepository.findById(id).orElse(null);
        if (type != null) {
            type.setTypeName(typeName);
            type.setDescr(descr);

            typeRepository.save(type);
        }
        return "redirect:/type/typeList";
    }

    @RequestMapping("/foodlist/{typeId}")
    public String foodList(@RequestParam(value = "pageNum", defaultValue = "1", required = false)Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize,
                           @PathVariable Integer typeId,
                           Model model) {
        if (pageNum == null || pageNum.equals("") || pageNum < 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize.equals("") || pageSize < 0) {
            pageSize = 10;
        }
        System.out.println(typeId);

        PageHelper.startPage(pageNum, pageSize);
        List<Menu> list;
        list = menuService.getMenuListByTypeId(typeId);
        PageInfo<Menu> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        return "menu/menuItemList";

    }







}
