package com.rabbit.studyweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbit.model.pojo.User;
import com.rabbit.model.pojo.dto.UserDTO;
import com.rabbit.model.pojo.dto.UserPasswordDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from sys_user where username=#{username} and password=#{password}")
    UserDTO getUserByMessage(@Param("username") String username, @Param("password") String password);

    @Select("select * from sys_user where username like concat('%',replace(#{username},' ',''),'%') limit #{currentPage},#{pageSize}")
    List<User> getAllUser(@Param("username") String username,@Param("currentPage") int currentPage,@Param("pageSize") int pageSize);

    @Select("select count(*) from sys_user where username like concat('%',#{username},'%') ")
    int getUserCounts(@Param("username") String username);

    @Update("update sys_user set state=#{state} where id=#{id}")
    boolean updateState(Integer id,Boolean state);

    @Insert("insert into sys_user(username,password,email,telephone,role_id,state,nickname,avatar_url) " +
            "value(#{username},#{password},#{email},#{telephone},#{roleId},#{state},#{nickname},#{avatarUrl})")
    boolean addUser(User user);

    @Delete("delete from sys_user where id=#{id}")
    boolean deleteUser(Integer id);

    @Select("select * from sys_user where id=#{id}")
    UserDTO getUpdateUser(Integer id);

    @Update("update sys_user set username=#{username},password=#{password},email=#{email},telephone=#{telephone},role_id=#{roleId},nickname=#{nickname},avatar_url=#{avatarUrl} where id=#{id}")
    boolean updateUser(User user);

    @Select("select * from sys_user where username=#{username}")
    UserDTO getUserByUsername(String username);

    @Select("select count(username) as count from sys_user where username=#{username}")
    User isExistUsername(String username);

    @Update("update sys_user set password=#{newPassword} where username=#{username} and password=#{password}")
    boolean updatePassword(UserPasswordDTO userPasswordDTO);

    @Update("update sys_user set password=#{password} where telephone=#{telephone}")
    boolean updatePasswordByTel(User user);

}

