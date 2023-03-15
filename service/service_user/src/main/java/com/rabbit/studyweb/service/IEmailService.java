package com.rabbit.studyweb.service;

public interface IEmailService {
    String sendEmail(String from,String address,String title,String msg);
}
