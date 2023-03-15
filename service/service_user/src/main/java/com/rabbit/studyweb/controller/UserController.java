package com.rabbit.studyweb.controller;

import com.rabbit.studyweb.common.Constants;
import com.rabbit.studyweb.result.R;
import com.rabbit.model.pojo.QueryInfo;
import com.rabbit.model.pojo.User;
import com.rabbit.model.pojo.dto.UserDTO;
import com.rabbit.model.pojo.dto.UserPasswordDTO;
import com.rabbit.studyweb.service.IRoleService;
import com.rabbit.studyweb.service.UserService;
import com.rabbit.studyweb.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rabbit.studyweb.common.Constants.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public R<Object> getUserList(QueryInfo queryInfo){
        //获取最大列表数和当前编号
        int userCounts = userService.getUserCounts(queryInfo.getQuery() );
        int currentPage = queryInfo.getPageSize() * (queryInfo.getCurrentPage() - 1);

        List<User> users = userService.getAllUser( queryInfo.getQuery() , currentPage, queryInfo.getPageSize());
        users.forEach(user -> {
            user.setRoleName(roleService.getById(user.getRoleId()).getName());
        });

        return R.success(users,userCounts);
    }

    @PutMapping ("/userState")
    public R<String> updateState(@RequestParam("id") Integer id,@RequestParam("state") Boolean state){
        boolean flag = userService.updateState(id, state);
        if(flag){
            return R.success(updateSuc_MSG);
        }else{
            return R.error(updateErr_MSG);
        }

    }

    @PostMapping("/addUser")
    public R<String> addUser(@RequestBody User user){
        boolean flag = userService.addUser(user);
        if(!flag){
            return R.error(addErr_MSG);
        }
        return R.success(addSuc_MSG);
    }

    @DeleteMapping("/deleteUser")
    public R<String> deleteUser(Integer id){
        boolean flag = userService.deleteUser(id);
        if(!flag){
            return R.error(delErr_MSG);
        }
        return R.success(delSuc_MSG);
    }


    @GetMapping("/getUpdate")
    public R<UserDTO> getUpdateUser(Integer id){
        UserDTO userDTO = userService.getUpdateUser(id);
        return R.success(userDTO);
    }

    @PutMapping("/updateUser")
    public R<String> updateUser(@RequestBody User user){
        boolean flag = userService.updateUser(user);
        if(!flag){
            return R.error(updateErr_MSG);
        }
        return R.success(updateSuc_MSG);
    }

    @GetMapping("/username/{username}")
    public R<UserDTO>  findUserByUsername(@PathVariable String username){
        return R.success(userService.getUserByUsername(username));
    }

    //添加用户前先判断用户是否存在
    @GetMapping("/isExistUsername")
    public R<String> isExistUsername(String username){
        User user = userService.isExistUsername(username);
        if(user.getCount()==0){
            return R.error(Constants.no_exist_username);
        }
        return R.success(Constants.exist_username);
    }
    
    @PostMapping("/password")
    public R<String> password(@RequestBody UserPasswordDTO userPasswordDTO){
        boolean flag = userService.updatePassword(userPasswordDTO);
        if(!flag){
            return R.error(Constants.updatePasswordErr_MSG);
        }
        return R.success(updatePasswordSuc_MSG);
    }

    @PostMapping("/updatePassword")
    public R<String> updatePassword(@RequestBody UserDTO userDTO){
        boolean flag = userService.updatePasswordByTel(userDTO);
        if(!flag){
            return R.error(updatePasswordByTelErr_MSG);
        }
        return R.success(updatePasswordByTelSuc_MSG);
    }

    //远程调用
    @GetMapping("/inner/getUser")
    public User getUserInfo(){
        User user = TokenUtils.getCurrentUser();
        return user;
    }


}
