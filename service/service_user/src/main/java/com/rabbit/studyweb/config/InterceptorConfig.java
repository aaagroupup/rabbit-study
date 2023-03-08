package com.rabbit.studyweb.config;

import com.rabbit.studyweb.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    //放行路径
    private static final String[] EXCLUDE_PATH={
            "/teacher/inner/**",
            "/swagger-ui/**",
            "/common/**",
            "/login/**",
            "/aliPay/**",
            "/user/updatePassword",
            "/subject/**",
            "/vod/**",
            "/home/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")  // 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns(EXCLUDE_PATH)
                .excludePathPatterns("/**/*.html","/**/*.css","/**/*.js");//放行静态资源
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
