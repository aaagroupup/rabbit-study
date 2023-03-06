package com.rabbit.studyweb.live;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.rabbit.studyweb.live.mapper")
@EnableDiscoveryClient
@EnableFeignClients("com.rabbit")
public class LiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiveApplication.class,args);
    }
}
