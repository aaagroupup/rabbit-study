package com.rabbit.studyweb.controller;

import cn.hutool.core.lang.id.NanoId;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.studyweb.common.Constants;
import com.rabbit.studyweb.result.R;
import com.rabbit.model.pojo.Role;
import com.rabbit.model.pojo.SubMenu;
import com.rabbit.model.pojo.User;
import com.rabbit.model.pojo.dto.UserDTO;
import com.rabbit.studyweb.service.*;
import com.rabbit.studyweb.utils.SmsConfig;
import com.rabbit.studyweb.utils.TokenUtils;
import com.rabbit.studyweb.utils.ValidateCodeUtils;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Autowired
    private SubMenuService subMenuService;

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
        if(StrUtil.isBlank(phone)){
            return R.error(Constants.sendMsgErr);
        }
        // 发送短信
        String url=smsConfig.getUrl();
        String appId= smsConfig.getAppId();
        String appS= smsConfig.getAppSecret();

        ZhenziSmsClient client = new ZhenziSmsClient(url,appId,appS);
        Map<String,Object> params = new HashMap<>();
        params.put("number",phone);
        params.put("templateId",smsConfig.getTemplateId());
        String [] templateParams = new String[2];
        Integer code = ValidateCodeUtils.generateValidateCode(4);//随机生成四位验证码
        log.info("code={}",code);
        templateParams[0] = code.toString();
        templateParams[1] = "2分钟";
        params.put("templateParams",templateParams);

        client.send(params);
        redisTemplate.opsForValue().set(phone,code.toString(),2,TimeUnit.MINUTES);

        return R.success(code,Constants.send_msg_success);
    }

    /**
     * 移动端用户登录
     * @param userDTO
     * @return
     */
    @PostMapping("/telLogin")
    public R<User> loginByPhone(@RequestBody UserDTO userDTO){
        //获取手机号
        String phone = userDTO.getTelephone();

        //获取验证码
        String code = userDTO.getCode().toString();

        //从Redis中获取缓存的验证码
        String  codeSession= (String) redisTemplate.opsForValue().get(phone);

        //进行验证码比对
        if(codeSession!=null&&codeSession.equals(code)){
            //如果比对成功，说明登录成功

            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getTelephone,phone);
            User oneUser = userService.getOne(wrapper);

            //设置token
            String token = TokenUtils.genToken(oneUser.getId().toString(), oneUser.getPassword());
            //根据角色信息查询出角色id
            String roleName = oneUser.getRole();
            LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Role::getName,roleName);
            Role role = roleService.getOne(queryWrapper);
            Integer roleId = role.getId();
            //根据角色id查询出所有菜单id
            List<Integer> menuIds = roleMenuService.selectByRoleId(roleId);
            List<SubMenu> menus = subMenuService.listByIds(menuIds);

            UserDTO user = new UserDTO();
            user.setId(oneUser.getId());
            user.setUsername(oneUser.getUsername());
            user.setNickname(oneUser.getNickname());
            user.setPassword(oneUser.getPassword());
            user.setRole(oneUser.getRole());
            user.setState(oneUser.isState());
            user.setTelephone(oneUser.getTelephone());
            user.setAvatarUrl(oneUser.getAvatarUrl());
            user.setToken(token);
            user.setMenus(menus);

            //如果用户登录成功，删除Redis中缓存的验证码
            redisTemplate.delete(phone);

            return R.success(user,Constants.loginSuc_MSG);
        }
        return R.error(Constants.loginErr_MSG);
    }

    @PostMapping("/register")
    public R<String> register(@RequestBody UserDTO userDTO){

        //获取手机号
        String phone = userDTO.getTelephone();

        //获取验证码
        String code = userDTO.getCode().toString();
        //从Redis中获取缓存的验证码
        String  codeSession= (String) redisTemplate.opsForValue().get(phone);
        User user = new User();
        //进行验证码比对
        if(codeSession!=null&&codeSession.equals(code)) {
            //如果比对成功，说明验证码正确
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());
            user.setRole(Constants.default_role);
            user.setState(true);
            user.setTelephone(userDTO.getTelephone());
            user.setAvatarUrl(Constants.default_avatar);
            if(StrUtil.isBlank(userDTO.getNickname())){//如果用户没有设置昵称，那个自动给一个默认的昵称
                String nanoId = NanoId.randomNanoId(14);//胡图工具随机生成14位字符串
                Random random = new Random();
                int num = random.nextInt(9) + 1;//随机生成1-9
                String nickname="用户_0"+num+"+_"+nanoId;
                user.setNickname(nickname);
            }else{
                user.setNickname(userDTO.getNickname());
            }
        }
        boolean flag = userService.save(user);
        if(!flag){
            return R.error(Constants.registerErr_MSG);
        }
        //如果用户注册成功，删除Redis中缓存的验证码
        redisTemplate.delete(phone);
        return R.success(Constants.registerSuc_MSG);
    }
}
