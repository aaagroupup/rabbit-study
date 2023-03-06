package com.rabbit.studyweb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.studyweb.result.R;
import com.rabbit.model.pojo.Icon;
import com.rabbit.model.pojo.MainMenu;
import com.rabbit.model.pojo.QueryInfo;
import com.rabbit.model.pojo.dto.SubMenuDTO;
import com.rabbit.studyweb.service.IconService;
import com.rabbit.studyweb.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rabbit.studyweb.common.Constants.*;

@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;


    @GetMapping("/menus")
    public R<List<MainMenu>> getAllMenus(){

        List<MainMenu> menus = menuService.getMenus();
        if (menus!=null){
            return R.success(menus);
        }else {
            return R.error(menuErr_MSG);
        }
    }

    @GetMapping
    public R<Object> getMenuList(QueryInfo queryInfo){
        //获取最大列表数和当前编号
        int MenuCounts = menuService.getMenuCounts(queryInfo.getQuery() );
        int currentPage = queryInfo.getPageSize() * (queryInfo.getCurrentPage() - 1);

        List<SubMenuDTO> Menus = menuService.getAllMenu( queryInfo.getQuery() , currentPage, queryInfo.getPageSize());

        return R.success(Menus,MenuCounts);
    }

    @PostMapping("/addMenu")
    public R<String> addMenu(@RequestBody SubMenuDTO Menu){
        boolean flag = menuService.addMenu(Menu);
        if(!flag){
            return R.error(addErr_MSG);
        }
        return R.success(addSuc_MSG);
    }

    @DeleteMapping("/deleteMenu")
    public R<String> deleteMenu(Integer id){
        boolean flag = menuService.deleteMenu(id);
        if(!flag){
            return R.error(delErr_MSG);
        }
        return R.success(delSuc_MSG);
    }

    @GetMapping("/getUpdate")
    public R<SubMenuDTO> getUpdateMenu(Integer id){
        SubMenuDTO menu = menuService.getUpdateMenu(id);
        return R.success(menu);
    }

    @PutMapping("/updateMenu")
    public R<String> updateMenu(@RequestBody SubMenuDTO menu){
        boolean flag = menuService.updateMenu(menu);
        if(!flag){
            return R.error(updateErr_MSG);
        }
        return R.success(updateSuc_MSG);
    }

    @Autowired
    private IconService iconService;

    @GetMapping("/icons")
    public R<List<Icon>> getIcons(){
        LambdaQueryWrapper<Icon> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Icon::getType,icon_type);
        List<Icon> iconList = iconService.list(wrapper);
        return R.success(iconList);
    }

    /**
     * 获取父路径
     * @return
     */
    @GetMapping("/getFPath")
    public R<List<MainMenu>> getFPath(){
        List<MainMenu> menus = menuService.getFPath();
        return R.success(menus);
    }

}
