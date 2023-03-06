package com.rabbit.studyweb.utils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AlipayConfig {
    @Value("${alipay.appId}")
    private String appId;
    @Value("${alipay.appPrivateKey}")
    private String appPrivateKey;
    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${alipay.returnUrl}")
    private String returnUrl;
}
