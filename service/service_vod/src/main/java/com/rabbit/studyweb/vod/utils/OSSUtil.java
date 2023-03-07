package com.rabbit.studyweb.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OSSUtil implements InitializingBean {

    @Value("${studyweb.endpoint}")
    private String endpoint;
    @Value("${studyweb.secretid}")
    private String secretId;

    @Value("${studyweb.secretkey}")
    private String secretKey;

    @Value("${studyweb.bucketname}")
    private String bucketName;
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID=secretId;
        END_POINT=endpoint;
        ACCESS_KEY_SECRET=secretKey;
        BUCKET_NAME=bucketName;
    }
}
