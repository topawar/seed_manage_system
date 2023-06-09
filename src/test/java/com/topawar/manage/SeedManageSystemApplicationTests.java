package com.topawar.manage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.topawar.manage.domain.User;
import com.topawar.manage.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SeedManageSystemApplicationTests {

    @Resource
    UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void getcodeTest() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getName,"万叶");
        queryWrapper.lambda().eq(User::getPassword,"WY20030125");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

}
