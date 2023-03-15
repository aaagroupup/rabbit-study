package com.rabbit.studyweb.service.impl;

import com.rabbit.studyweb.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender jms;

    @Override
    public String sendEmail(String from,String address,String title,String msg) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(address); // 接收地址
            message.setSubject(title); // 标题
            message.setText(msg); // 内容
            jms.send(message);
            return "邮箱发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
