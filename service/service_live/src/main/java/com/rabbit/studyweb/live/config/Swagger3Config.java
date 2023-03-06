package com.rabbit.studyweb.live.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;

@Configuration
//@EnableSwagger2
@EnableSwagger2WebMvc
public class Swagger3Config {
    /**
     *
     * 配置swagger的Docket bean
     * @return
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30) // 指定swagger3.0版本
                .enable(true)//开关
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)) // 指定扫描的包常用方式
                .paths(PathSelectors.any()) // 匹配 /rabbit/**请求路径
                .build()
                .groupName("1.0版本")
                .apiInfo(apiInfo());
    }

    /**
     * 配置swagger的ApiInfo bean
     */
    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfo(
                "在线学习网站-API文档"
                ,"这是学习平台的API接口文档"
                ,"1.0"
                ,""
                ,new Contact("rabbit", "",
                "2559525655@qq.com")
                ,"Apache 2.0"
                ,"http://www.apache.org/licenses/LICENSE-2.0"
                ,new ArrayList());
    }
}