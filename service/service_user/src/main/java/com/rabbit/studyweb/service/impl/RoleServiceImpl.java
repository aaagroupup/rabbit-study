package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.studyweb.mapper.RoleMapper;
import com.rabbit.studyweb.mapper.RoleMenuMapper;
import com.rabbit.model.pojo.Role;
import com.rabbit.model.pojo.RoleMenu;
import com.rabbit.studyweb.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rabbit
 * @since 2022-09-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public int getRoleCounts(String query) {
        return roleMapper.getRoleCounts(query);
    }

    @Override
    public List<Role> getAllRole(String query, int currentPage, int pageSize) {
        return roleMapper.getAllRole(query,currentPage,pageSize);
    }

    @Override
    public boolean addRole(Role role) {
        return roleMapper.addRole((role));
    }

    @Override
    public boolean deleteRole(Integer id) {
        return roleMapper.deleteRole(id);
    }

    @Override
    public Role getUpdateRole(Integer id) {
        return roleMapper.getUpdateRole(id);
    }

    @Override
    public boolean updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    @Transactional
    @Override
    public boolean setRoleMenu(Integer roleId, List<Integer> menuIds) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,roleId);
        //先删除当前角色id所有的绑定关系
        int deleteNum = roleMenuMapper.delete(wrapper);
//        if(deleteNum!=0){
//            //再把前端传过来的菜单id数组绑定到当前角色id
//            for (Integer menuId : menuIds) {
//                RoleMenu roleMenu = new RoleMenu();
//                roleMenu.setRoleId(roleId);
//                roleMenu.setMenuId(menuId);
//                roleMenuMapper.insert(roleMenu);
//            }
//        }else{//原来的普通用户本身没有绑定菜单，所有deleteNum肯定为0
//            return false;
//          }
        for (Integer menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
            }
        return true;
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
