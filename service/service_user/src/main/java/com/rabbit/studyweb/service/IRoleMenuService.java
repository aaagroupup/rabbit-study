package com.rabbit.studyweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbit.model.pojo.RoleMenu;


import java.util.List;

/**
 * <p>
 * 角色菜单关系表 服务类
 * </p>
 *
 * @author rabbit
 * @since 2022-09-21
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    List<Integer> selectByRoleId(Integer roleId);
}
