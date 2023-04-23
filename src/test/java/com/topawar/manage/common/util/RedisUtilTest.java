package com.topawar.manage.common.util;

import com.topawar.manage.SeedManageSystemApplication;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author: YJ
 * @date: 2023/04/11 21:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SeedManageSystemApplication.class)
public class RedisUtilTest {
    @Resource
    RedisUtil redisUtil;

    @Resource
    CosUtil cosUtil;

    @Test
    public void setKeyExpire() {
        String key = null;
        long time = 200;
        redisUtil.expire(key, time);
    }

    @Test
    public void haskeyTest(){
        String key = "c52e2d4e-e717-4b73-adc2-48a27347d1d5";
        redisUtil.hasKey(key);
    }

    @Test
    public void getExpire(){
        System.out.println(redisUtil.getExprie("cos:test"));
    }

    @Test
    public void set(){
        redisUtil.set("cos:test","2332",200, TimeUnit.SECONDS);
    }

    @Test
    public void rangeTest(){
        cosUtil.setTempSecretKeyAndSecretIdAndSessionToken();
    }
}