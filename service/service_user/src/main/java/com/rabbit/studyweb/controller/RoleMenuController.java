package com.rabbit.studyweb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbit.model.pojo.RoleMenu;
import com.rabbit.studyweb.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2022-09-21
 */
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {
    @Autowired
    private IRoleMenuService roleMenuService;

     // 新增或者更新
     @PostMapping
     public boolean save(@RequestBody RoleMenu roleMenu) {
         return roleMenuService.saveOrUpdate(roleMenu);
     }

      @DeleteMapping("/{id}")
      public Boolean delete(@PathVariable Integer id) {
          return roleMenuService.removeById(id);
      }

      @PostMapping("/del/batch")
      public boolean deleteBatch(@RequestBody List<Integer> ids) {
          return roleMenuService.removeByIds(ids);
      }

      @GetMapping
      public List<RoleMenu> findAll() {
          return roleMenuService.list();
      }

      @GetMapping("/{id}")
      public RoleMenu findOne(@PathVariable Integer id) {
          return roleMenuService.getById(id);
      }

      @GetMapping("/page")
      public Page<RoleMenu> findPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize) {
          QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
          queryWrapper.orderByDesc("id");
          return roleMenuService.page(new Page<>(pageNum, pageSize), queryWrapper);
      }

}

