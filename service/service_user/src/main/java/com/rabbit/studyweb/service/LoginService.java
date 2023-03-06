package com.rabbit.studyweb.service;


import com.rabbit.model.pojo.User;
import com.rabbit.model.pojo.dto.UserDTO;
import com.rabbit.studyweb.result.R;

public interface LoginService {
    R<User> login(UserDTO user);
}
