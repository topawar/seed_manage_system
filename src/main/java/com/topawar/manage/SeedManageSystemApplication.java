package com.topawar.manage;

import com.topawar.manage.common.util.RedisUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
