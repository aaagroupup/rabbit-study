package com.rabbit.studyweb.service.impl;

import cn.hutool.core.lang.id.NanoId;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbit.model.pojo.CountTime;
import com.rabbit.model.pojo.Role;
import com.rabbit.model.pojo.SubMenu;
import com.rabbit.model.pojo.User;
import com.rabbit.model.pojo.dto.UserDTO;
import com.rabbit.studyweb.common.Constants;
import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.*;
import com.rabbit.studyweb.utils.MD5Util;
import com.rabbit.studyweb.utils.SmsConfig;
import com.rabbit.studyweb.utils.TokenUtils;
import com.rabbit.studyweb.utils.ValidateCodeUtils;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.rabbit.studyweb.common.Constants.*;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Autowired
    private SubMenuService subMenuService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private ICountTimeService countTimeService;

    @Autowired
    private IEmailService emailService;

    @Value("${spring.mail.username}")
    private String from;

    //账户登录
    @Override
    public R<User> login(UserDTO user) {
        String userPwd = MD5Util.getMd5Plus(user.getPassword());
        UserDTO oneUser = userService.getUserByMessage(user.getUsername(), userPwd);
//        log.info("user={}",oneUser);

        if (oneUser!=null){
            if(!oneUser.isState()){
                return R.error(login_State_Msg);
            }
            //设置token
            String token = TokenUtils.genToken(oneUser.getId().toString(), oneUser.getPassword());
            oneUser.setToken(token);

            //根据角色信息查询出角色id
            Integer roleId = oneUser.getRoleId();
            //根据角色id查询出所有菜单id
            List<Integer> menuIds = roleMenuService.selectByRoleId(roleId);
            List<SubMenu> menus = subMenuService.listByIds(menuIds);
            //log.info("menus={}",menus);
            oneUser.setMenus(menus);

            setLoginCount();
            return R.success(oneUser,loginSuc_MSG);
        }else {
            return R.error(loginErr_MSG);
        }
    }

    //手机号登录
    @Override
    public R<User> loginByTel(UserDTO userDTO) {
        //获取手机号
        String phone = userDTO.getTelephone();
        //根据手机号查询用户是否被禁用
        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(User::getTelephone,phone);
        User oneUser2 = userService.getOne(queryWrapper2);
        if(!oneUser2.isState()){
            return R.error(login_State_Msg);
        }

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
            Integer roleId = oneUser.getRoleId();
            //根据角色id查询出所有菜单id
            List<Integer> menuIds = roleMenuService.selectByRoleId(roleId);
            List<SubMenu> menus = subMenuService.listByIds(menuIds);

            UserDTO user = new UserDTO();
            user.setId(oneUser.getId());
            user.setUsername(oneUser.getUsername());
            user.setNickname(oneUser.getNickname());
            user.setPassword(oneUser.getPassword());
            user.setRoleId(oneUser.getRoleId());
            user.setState(oneUser.isState());
            user.setTelephone(oneUser.getTelephone());
            user.setAvatarUrl(oneUser.getAvatarUrl());
            user.setToken(token);
            user.setMenus(menus);

            //设置登录人数+1
            setLoginCount();

            //如果用户登录成功，删除Redis中缓存的验证码
            redisTemplate.delete(phone);

            return R.success(user, Constants.loginSuc_MSG);
        }
        return R.error(Constants.loginErr_MSG);
    }

    //发送短信
    @Override
    public R<Integer> sendMsg(String phone) throws Exception {
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
        redisTemplate.opsForValue().set(phone,code.toString(),2, TimeUnit.MINUTES);

        return R.success(code,Constants.send_msg_success);
    }

    //注册
    @Override
    public R<String> register(UserDTO userDTO) {
        //获取手机号
        String phone = userDTO.getTelephone();

        //获取验证码
        String code = userDTO.getCode().toString();

        //对密码进行加密
        String password = MD5Util.getMd5Plus(userDTO.getPassword());
        //从Redis中获取缓存的验证码
        String  codeSession= (String) redisTemplate.opsForValue().get(phone);
        User user = new User();
        //先进行手机号验证，判断是否已经存在
        boolean isExist= userService.findTelIsExist(phone);
        if(isExist){
            return R.error(TEL_EXIST);
        }
        //进行验证码比对
        if(codeSession!=null&&codeSession.equals(code)) {
            //如果比对成功，说明验证码正确
            user.setUsername(userDTO.getUsername());
            user.setPassword(password);
            user.setEmail(userDTO.getEmail());
            user.setRoleId(5);
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
        //通知邮箱
        emailService.sendEmail(from,user.getEmail(),"注册通知", REGISTER_EMAIL);
        //如果用户注册成功，删除Redis中缓存的验证码
        redisTemplate.delete(phone);

        return R.success(Constants.registerSuc_MSG);
    }

    //设置登录人数+1
    private void setLoginCount() {

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        String time=year+"年"+month+"月";

        LambdaQueryWrapper<CountTime> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CountTime::getTime,time);
        CountTime one = countTimeService.getOne(wrapper);
        CountTime countTime;
        if(one==null){
            countTime = new CountTime();
            countTime.setCount(1);
            countTime.setTime(time);
            countTimeService.save(countTime);
        }else{
            one.setCount(one.getCount()+1);
            countTimeService.updateById(one);
        }
    }
}
