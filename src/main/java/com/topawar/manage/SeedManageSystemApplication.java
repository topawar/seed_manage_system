package com.topawar.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: topawar
 */
@SpringBootApplication
@MapperScan("com.topawar.manage.mapper")
public class SeedManageSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeedManageSystemApplication.class, args);
    }

}
