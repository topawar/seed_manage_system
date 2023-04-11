package com.topawar.manage.common.util;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * @author: YJ
 * @date: 2023/04/11 21:16
 */
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Boolean expire(String key, long time) {
        Assert.notNull(key, "key is not null");
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }
}
