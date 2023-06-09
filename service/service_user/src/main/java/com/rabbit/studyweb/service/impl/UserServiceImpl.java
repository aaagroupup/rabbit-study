package com.rabbit.studyweb.service.impl;

import cn.hutool.core.lang.id.NanoId;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbit.model.pojo.Role;
import com.rabbit.model.pojo.SubMenu;
import com.rabbit.model.pojo.User;
import com.rabbit.model.pojo.dto.UserDTO;
import com.rabbit.model.pojo.dto.UserPasswordDTO;
import com.rabbit.studyweb.common.Constants;
import com.rabbit.studyweb.mapper.UserMapper;
import com.rabbit.studyweb.service.IRoleMenuService;
import com.rabbit.studyweb.service.IRoleService;
import com.rabbit.studyweb.service.SubMenuService;
import com.rabbit.studyweb.service.UserService;
import com.rabbit.studyweb.utils.MD5Util;
import com.rabbit.studyweb.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private SubMenuService subMenuService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public UserDTO getUserByMessage(String username, String password) {
        return userMapper.getUserByMessage(username,password);
    }

    @Override
    public List<User> getAllUser(String username, int currentPage, int pageSize) {
        return userMapper.getAllUser(username,currentPage,pageSize);
    }

    @Override
    public int getUserCounts(String username) {
        return userMapper.getUserCounts(username);
    }

    @Override
    public boolean updateState(Integer id, Boolean state) {
        return userMapper.updateState(id,state);
    }

    @Override
    public boolean addUser(User user) {
        if(StrUtil.isBlank(user.getNickname())){//如果用户没有指定昵称，那么就随机生成一个昵称
            String nanoId = NanoId.randomNanoId(14);//胡图工具随机生成14位字符串
            Random random = new Random();
            int num = random.nextInt(9) + 1;//随机生成1-9
            String nickname="用户_0"+num+"+_"+nanoId;
            user.setNickname(nickname);
        }
        if(StrUtil.isBlank(user.getAvatarUrl())){//给新添加的用户提供一个默认的头像
            user.setAvatarUrl(Constants.default_avatar);
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getTelephone,user.getTelephone());
        User one = this.getOne(wrapper);
        if(one!=null){
            return false;
        }
        //设置用户状态
        user.setState(true);
        //用户没有指定角色，默认为普通成员
        user.setRoleId(5);
        user.setPassword(MD5Util.getMd5Plus(user.getPassword()));
        return userMapper.addUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getTelephone,user.getTelephone());
        User one = this.getOne(wrapper);
        if(one!=null){
            return false;
        }
        return userMapper.updateUser(user);
    }

    @Override
    public UserDTO getUpdateUser(Integer id) {
        return userMapper.getUpdateUser(id);
    }

    @Override
    public boolean deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }


    @Override
    public UserDTO getUserByUsername(String username) {
        //查询出用户信息
        UserDTO user = userMapper.getUserByUsername(username);
        //给用户设置token和菜单
        //设置token
        String token = TokenUtils.genToken(user.getId().toString(), user.getPassword());
        user.setToken(token);

        //根据角色信息查询出角色id

        Integer roleId = user.getRoleId();
        //根据角色id查询出所有菜单id
        List<Integer> menuIds = roleMenuService.selectByRoleId(roleId);
        List<SubMenu> menus = subMenuService.listByIds(menuIds);
        user.setMenus(menus);
        return user;
    }

    @Override
    public User isExistUsername(String username) {
        return userMapper.isExistUsername(username);
    }

    @Override
    public boolean updatePassword(UserPasswordDTO userPasswordDTO){
        String newPwd = MD5Util.getMd5Plus(userPasswordDTO.getNewPassword());
        String oldPwd = MD5Util.getMd5Plus(userPasswordDTO.getPassword());
        userPasswordDTO.setPassword(oldPwd);
        userPasswordDTO.setNewPassword(newPwd);
        return userMapper.updatePassword(userPasswordDTO);
    }

    @Override
    public boolean updatePasswordByTel(UserDTO userDTO) {
        //获取手机号
        String phone = userDTO.getTelephone();
        //获取验证码
        String code = userDTO.getCode().toString();
        //从Redis中获取缓存的验证码
        String  codeSession= (String) redisTemplate.opsForValue().get(phone);

        //进行验证码比对
        if(codeSession!=null&&codeSession.equals(code)){
            //如果比对成功，说明验证码正确可以进行修改

            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getTelephone,phone);
            User oneUser = this.getOne(wrapper);

            if (oneUser == null) {
                return false;
            } else {
                User user = new User();
                String pwd = MD5Util.getMd5Plus(userDTO.getPassword());
                user.setPassword(pwd);
                user.setTelephone(phone);
                //如果用户修改成功，删除Redis中缓存的验证码
                redisTemplate.delete(phone);
                return userMapper.updatePasswordByTel(user);
            }
        }else{
            return false;
        }

    }

    //查找手机号是否存在
    @Override
    public boolean findTelIsExist(String phone) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getTelephone,phone);
        User user = baseMapper.selectOne(wrapper);
        return user != null;
    }
}
