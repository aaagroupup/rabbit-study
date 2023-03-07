package com.rabbit.studyweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.rabbit")
@EnableFeignClients
public class RabbitStudyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitStudyWebApplication.class, args);
    }

}

