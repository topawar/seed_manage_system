package com.topawar.manage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
  
  
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogHandlerInterceptor())
                .addPathPatterns("/**")  
                .excludePathPatterns(    //添加不拦截路径  
                "/user/login"                //登录路径
        );;  
    }  
}