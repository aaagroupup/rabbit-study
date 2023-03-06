package com.rabbit.studyweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.model.pojo.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rabbit
 * @since 2022-09-19
 */

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select * from sys_role where name like concat('%',replace(#{name},' ',''),'%') limit #{currentPage},#{pageSize}")
    List<Role> getAllRole(@Param("name") String name, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    @Select("select count(*) from sys_role where name like concat('%',#{name},'%') ")
    int getRoleCounts(@Param("name") String name);


    @Insert("insert into sys_role(name,description) " +
            "value(#{name},#{description})")
    boolean addRole(Role role);

    @Delete("delete from sys_role where id=#{id}")
    boolean deleteRole(Integer id);

    @Select("select * from sys_role where id=#{id}")
    Role getUpdateRole(Integer id);

    @Update("update sys_role set name=#{name},description=#{description} where id=#{id}")
    boolean updateRole(Role role);
}
