package com.rabbit.studyweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rabbit.model.pojo.User;
import com.rabbit.model.pojo.dto.UserDTO;
import com.rabbit.model.pojo.dto.UserPasswordDTO;

import java.util.List;

public interface UserService extends IService<User> {
    UserDTO getUserByMessage(String username, String password);
    List<User> getAllUser(String username, int currentPage,int pageSize);
    int getUserCounts(String username);
    boolean updateState(Integer id,Boolean state);
    boolean addUser(User user);
    boolean updateUser(User user);

    UserDTO getUpdateUser(Integer id);

    boolean deleteUser(Integer id);

    UserDTO getUserByUsername(String username);

    User isExistUsername(String username);

    boolean updatePassword(UserPasswordDTO userPasswordDTO);

    boolean updatePasswordByTel(UserDTO userDTO);

    boolean findTelIsExist(String phone);
}
