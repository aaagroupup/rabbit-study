package com.rabbit.studyweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.Role;
import com.rabbit.model.pojo.SubMenu;
import com.rabbit.model.pojo.User;
import com.rabbit.model.pojo.dto.UserDTO;
import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.*;
import com.rabbit.studyweb.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rabbit.studyweb.common.Constants.*;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private SubMenuService subMenuService;
    @Override
    public R<User> login(UserDTO user) {
        UserDTO oneUser = userService.getUserByMessage(user.getUsername(), user.getPassword());
//        log.info("user={}",oneUser);

        if (oneUser!=null){
            if(!oneUser.isState()){
                return R.error(login_State_Msg);
            }
            //设置token
            String token = TokenUtils.genToken(oneUser.getId().toString(), oneUser.getPassword());
            oneUser.setToken(token);

            //根据角色信息查询出角色id
            String roleName = oneUser.getRole();
            LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Role::getName,roleName);
            Role role = roleService.getOne(wrapper);
            Integer roleId = role.getId();
            //根据角色id查询出所有菜单id
            List<Integer> menuIds = roleMenuService.selectByRoleId(roleId);
            List<SubMenu> menus = subMenuService.listByIds(menuIds);
            //log.info("menus={}",menus);
            oneUser.setMenus(menus);

            return R.success(oneUser,loginSuc_MSG);
        }else {
            return R.error(loginErr_MSG);
        }
    }
}
