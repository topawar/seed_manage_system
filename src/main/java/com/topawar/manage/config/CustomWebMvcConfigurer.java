package com.topawar.manage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initAuthenticHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/swagger-resources/**", "/webjars/**", "/v3/**", "/swagger-ui.html/**");
    }

    @Bean
    public AuthenticHandlerInterceptor initAuthenticHandlerInterceptor() {
        return new AuthenticHandlerInterceptor();
    }
}