package com.rabbit.studyweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbit.model.pojo.Role;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rabbit
 * @since 2022-09-19
 */
public interface IRoleService extends IService<Role> {

    int getRoleCounts(String query);

    List<Role> getAllRole(String query, int currentPage, int pageSize);


    boolean addRole(Role role);

    boolean deleteRole(Integer id);

    Role getUpdateRole(Integer id);

    boolean updateRole(Role role);

    boolean setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
