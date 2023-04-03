package com.topawar.manage.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.topawar.manage.domain.User;
import com.topawar.manage.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author: topawar
 * @date: 2022/12/14 20:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ResponseCodeTest {

    @Resource
    UserMapper userMapper;

    @Test
    public void getcodeTest() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","万叶");
        queryWrapper.eq("password","WY20030125");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }
}
