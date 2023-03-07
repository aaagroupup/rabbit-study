package com.rabbit.studyweb.controller;

import com.rabbit.studyweb.common.Constants;
import com.rabbit.studyweb.result.R;
import com.rabbit.model.pojo.User;
import com.rabbit.model.pojo.dto.UserDTO;
import com.rabbit.studyweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/accountLogin")
    public R<User> login(@RequestBody UserDTO user){
        return loginService.login(user);
    }

    @GetMapping("/exit")
    public R<String> exit(){
        return R.success(Constants.exit_Msg);
    }



    @GetMapping("/sendMsg")
    public R<Integer> sendMsg(@RequestParam String phone) throws Exception {
        return loginService.sendMsg(phone);
    }

    /**
     * 移动端用户登录
     * @param userDTO
     * @return
     */
    @PostMapping("/telLogin")
    public R<User> loginByPhone(@RequestBody UserDTO userDTO){
        return loginService.loginByTel(userDTO);
    }

    @PostMapping("/register")
    public R<String> register(@RequestBody UserDTO userDTO){
        return loginService.register(userDTO);
    }
}
