package com.rabbit.studyweb.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class SmsConfig {
    @Value("${zhenzikj.url}")
    private String url;
    @Value("${zhenzikj.appId}")
    private String appId;
    @Value("${zhenzikj.appSecret}")
    private String appSecret;
    @Value("${zhenzikj.templateId}")
    private String templateId;
}
