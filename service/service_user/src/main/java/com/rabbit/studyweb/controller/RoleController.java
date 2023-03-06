package com.rabbit.studyweb.controller;

import com.rabbit.studyweb.result.R;
import com.rabbit.model.pojo.QueryInfo;
import com.rabbit.model.pojo.Role;
import com.rabbit.studyweb.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rabbit.studyweb.common.Constants.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rabbit
 * @since 2022-09-19
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @GetMapping
    public R<Object> getRoleList(QueryInfo queryInfo){
        //获取最大列表数和当前编号
        int roleCounts = roleService.getRoleCounts(queryInfo.getQuery() );
        int currentPage = queryInfo.getPageSize() * (queryInfo.getCurrentPage() - 1);

        List<Role> roles = roleService.getAllRole( queryInfo.getQuery() , currentPage, queryInfo.getPageSize());

        return R.success(roles,roleCounts);
    }


    @PostMapping("/addRole")
    public R<String> addRole(@RequestBody Role role){
        boolean flag = roleService.addRole(role);
        if(!flag){
            return R.error(addErr_MSG);
        }
        return R.success(addSuc_MSG);
    }

    @DeleteMapping("/deleteRole")
    public R<String> deleteRole(Integer id){
        boolean flag = roleService.deleteRole(id);
        if(!flag){
            return R.error(delErr_MSG);
        }
        return R.success(delSuc_MSG);
    }


    @GetMapping("/getUpdate")
    public R<Role> getUpdateRole(Integer id){
        Role role = roleService.getUpdateRole(id);
        return R.success(role);
    }

    @PutMapping("/updateRole")
    public R<String> updateRole(@RequestBody Role Role){
        boolean flag = roleService.updateRole(Role);
        if(!flag){
            return R.error(updateErr_MSG);
        }
        return R.success(updateSuc_MSG);
    }

    @PostMapping("/roleMenu/{roleId}")
    public R<String> setRoleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds){
        boolean flag = roleService.setRoleMenu(roleId, menuIds);
        if(!flag){
            return R.error(menuBindErr_MSG);
        }
        return R.success(menuBindSuc_MSG);
    }

    @GetMapping("/roleMenu/{roleId}")
    public R<List<Integer>> getRoleMenu(@PathVariable Integer roleId){
        List<Integer> roleMenuIds = roleService.getRoleMenu(roleId);
        return R.success(roleMenuIds);
    }

    /**
     * 获取所有的角色
     * @return
     */
    @GetMapping("roles")
    public R<List<Role>> getRoles(){
        List<Role> roleList = roleService.list();
        return R.success(roleList);
    }
}

