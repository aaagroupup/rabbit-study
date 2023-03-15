package com.rabbit.studyweb.controller;

import com.rabbit.studyweb.result.R;
import com.rabbit.studyweb.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mail")
public class EmailController {

    @Autowired
    private IEmailService emailService;


    @Value("${spring.mail.username}")
    private String from;

    @RequestMapping("sendSimpleEmail")
    public R sendSimpleTestEmail(String address,String title,String msg) {
        String suc= emailService.sendEmail(from,address,title,msg);
        return R.success(suc);
    }
}
