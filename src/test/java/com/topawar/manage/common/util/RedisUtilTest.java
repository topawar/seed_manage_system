package com.topawar.manage.common.util;

import com.topawar.manage.SeedManageSystemApplication;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: YJ
 * @date: 2023/04/11 21:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SeedManageSystemApplication.class)
public class RedisUtilTest {
    @Resource
    RedisUtil redisUtil;

    @Test
    public void setKeyExpire() {
        String key = null;
        long time = 200;
        redisUtil.expire(key, time);
    }
}