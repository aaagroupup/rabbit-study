package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.studyweb.mapper.RoleMenuMapper;
import com.rabbit.model.pojo.RoleMenu;
import com.rabbit.studyweb.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2022-09-21
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
    @Autowired
    private RoleMenuMapper roleMenuMapper;


    @Override
    public List<Integer> selectByRoleId(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
