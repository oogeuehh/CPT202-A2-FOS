package com.group28.orderingSystem.controller;


//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.group28.orderingSystem.model.Flavor;
import com.group28.orderingSystem.model.Type;
import com.group28.orderingSystem.model.User;
import com.group28.orderingSystem.repository.FlavorRepo;
import com.group28.orderingSystem.repository.TypeRepo;
import com.group28.orderingSystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.group28.orderingSystem.model.Menu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;
    @Autowired
    TypeRepo typeRepo;
    @Autowired
    FlavorRepo flavorRepo;

    @RequestMapping("/menuItemList") //全部+查询
    public String selectAll(Model model,
                            @RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "6") int pageSize,
                            @RequestParam(required = false) String keywords,
                            @RequestParam(required = false) Integer typeId){
        PageHelper.startPage(pageNum,pageSize);
        List<Menu> list;
        if (keywords != null && !keywords.isEmpty()) {
            list = menuService.getMenuList(keywords);
        } else if (typeId != null) { // 如果 typeId 不为空，则根据 typeId 查询对应的菜单列表
            list = menuService.getMenuListByTypeId(typeId);
        } else { // 否则，查询所有菜单列表
            list = menuService.selectAll();
        }

        PageInfo<Menu> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        List<Type> typeList = typeRepo.findAll();
        model.addAttribute("typeList",typeList);
        return "menu/menuItemList";
    }
    /*** Add ******************************************************/
    @RequestMapping("/addMenuItem")
    public String addMenuItem(Model model){
        List<Type> typeList = typeRepo.findAll();
        model.addAttribute("typeList",typeList);
        return "menu/addMenuItem";
    }

    @PostMapping("/insertMenuItem")
    public String insertUser(Menu menu,
                             @RequestParam(value = "spiciness", required = false) String spiciness,
                             @RequestParam(value = "cilantro", required = false) String cilantro,
                             @RequestParam(value = "greenOnion", required = false) String greenOnion,
                             @RequestParam(value = "garlic", required = false) String garlic){
         menu.setIsLoaded(1);
         Menu newMenu = menuService.insertMenuItem(menu);
        if (spiciness != null) {
            Flavor flavor = new Flavor(newMenu.getId(),"spiciness");
            flavorRepo.save(flavor);

        }
        if (cilantro != null) {
            Flavor flavor = new Flavor(newMenu.getId(),"cilantro");
            flavorRepo.save(flavor);
        }
        if (greenOnion != null) {
            Flavor flavor = new Flavor(newMenu.getId(),"greenOnion");
            flavorRepo.save(flavor);
        }
        if (garlic != null) {
            Flavor flavor = new Flavor(newMenu.getId(),"garlic");
            flavorRepo.save(flavor);
        }
        return "redirect:/menu/menuItemList";
    }
    /**** look for detail *************************************************************/

    @GetMapping("/detail/{id}")
    public String viewMenuItemDetail(Model model, @PathVariable Integer id){
        Menu menu = menuService.selectById(id);
        model.addAttribute("menu", menu);
        return "menu/menuDetailAdmin";
    }

    @GetMapping("/updateMenuItem/{id}")
    public String updateMenuItem(Model model, @PathVariable Integer id){
        Menu menu = menuService.selectById(id);
        List<Type> types = typeRepo.findAll();
        model.addAttribute("types", types);
        model.addAttribute("menu", menu);
        return "menu/updateMenuItem";
    }

    //返回更新后的菜单
    @PostMapping("/updateMenu")
    public String updateUser(@RequestParam("id") Integer id,
                             @RequestParam("name") String name,
                             @RequestParam("taste") String taste,
                             @RequestParam("smallPrice") Double smallPrice,
                             @RequestParam("mediumPrice") Double mediumPrice,
                             @RequestParam("largePrice") Double largePrice,
                             @RequestParam("descr") String descr,
                             @RequestParam("stock") Integer stock,
                             @RequestParam("type") Type type,
                             @RequestParam("isLoaded") Integer isLoaded,Model model){
        Menu menu = menuService.selectById(id);
        menu.setName(name);
        menu.setSmallPrice(smallPrice);
        menu.setMediumPrice(mediumPrice);
        menu.setLargePrice(largePrice);
        menu.setTaste(taste);
        menu.setDescr(descr);
        menu.setStock(stock);
        menu.setIsLoaded(isLoaded);
        menu.setType(type);
        menuService.updateMenu(menu);
        return "redirect:/menu/menuItemList";
    }

    //删除菜品，直接返回删除后的菜单
    @GetMapping("/deleteMenuItem/{id}")
    public String delete(@PathVariable Integer id){
        menuService.deleteUser(id);
        return "redirect:/menu/menuItemList";
    }

    @GetMapping("/menuDetail/{id}")
    public String detail(Model model, @PathVariable Integer id){
        Menu menu = menuService.selectById(id);
        model.addAttribute("menu", menu);
        return "menu/menuDetail";
    }

    @GetMapping("/editImage/{id}")
    public String editImagePage(@PathVariable Integer id, Model model) {
        // 根据 id 获取菜单项信息
        Menu menu = menuService.selectById(id);
        model.addAttribute("menu", menu);
        return "menu/editImage"; // 返回一个页面，用于修改和上传图片
    }

    @PostMapping("/updateImage/{id}")
    public String updateImage(@PathVariable Integer id, @RequestParam("newImage") MultipartFile newImage) {
        Menu menu = menuService.selectById(id);
        uploadImage(menu,newImage);
        menuService.updateMenu(menu);
        return "redirect:/menu/menuItemList"; // 重定向到菜单列表页面
    }
    private void uploadImage(Menu menu,MultipartFile newImage){

        if (!newImage.isEmpty()) {
            try {
                String originalfileName = newImage.getOriginalFilename();
                int index = originalfileName.lastIndexOf(".");
                String suffix = originalfileName.substring(index);
                String prefix = System.nanoTime()+"";
                String path = prefix+suffix;
                String filePath = "src/main/resources/static/image/";
                File file1= new File(filePath);
                if(!file1.exists()){
                    file1.mkdirs();
                }
                File file2 = new File(file1, path);
//                try{
//                    newImage.transferTo(file2);
//                }catch(IOException e){
//                    e.printStackTrace();
//                }
//                Menu menu = menuService.selectById(id);
                Files.copy(newImage.getInputStream(), Paths.get(filePath).resolve(path), StandardCopyOption.REPLACE_EXISTING);
                menu.setImage(path);
                // 更新数据库中图片的文件名
//                Menu menu = menuService.selectById(id);
//                menuService.updateMenu(menu);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
