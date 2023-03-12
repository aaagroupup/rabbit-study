package com.rabbit.studyweb.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class MD5Util {
    //必须要有Spring-core支持
    public static String getMd5Plus(String pwd){
        //先进行第一层加密
        String md1 = DigestUtils.md5DigestAsHex(pwd.getBytes());
        //截取第一层加密后的密文的前6位
        String substring6 = md1.substring(0, 6);//前包含后不包含
        //再对密文+前6位再加密
        return DigestUtils.md5DigestAsHex((md1+substring6).getBytes());
    }
}
