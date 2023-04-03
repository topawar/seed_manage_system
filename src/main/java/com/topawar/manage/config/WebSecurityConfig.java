//package com.topawar.manage.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
//
///**
// * @author: topawar
// */
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("ADMIN").build());
//        return manager;
//    }
//
//    @Bean
//    public SecurityFilterChain web(HttpSecurity http) throws Exception {
//        http.csrf().disable().
//                authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/resources/**", "/signup", "/about", "/user/**", "/api/**").permitAll()
//                        .requestMatchers("/once/**").hasRole("USER")
//                        .requestMatchers("/db/**").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') and hasRole('DBA')"))
//                        .anyRequest().denyAll()
//                );
//        return http.build();
//    }
//}