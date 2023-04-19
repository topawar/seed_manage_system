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

    /**
     * @param key
     * @param time
     * @return 设置key的过期时间
     */
    public Boolean expire(String key, long time) {
        Assert.notNull(key, "key is not null");
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @return 获取key的过期时间
     */
    public Long getExprie(String key) {
        Assert.notNull(key, "key is not null");
        return redisTemplate.getExpire(key);
    }

    /**
     * @param key
     * @return 获取key的是否存在
     */
    public Boolean hasKey(String key) {
        Assert.notNull(key, "key is not null");
        return redisTemplate.hasKey(key);
    }


    /**
     * @param key
     * @return key持久化
     */
    public Boolean persist(String key) {
        Assert.notNull(key, "key is not null");
        return redisTemplate.boundValueOps(key).persist();
    }

    /**
     * @param key
     * @param value
     */
    public void set(String key, Object value, long time,TimeUnit timeUnit) {
        Assert.notNull(key, "key is not null");
        if (time > 0){
            redisTemplate.opsForValue().set(key, value, time,timeUnit);
            return;
        }
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * @param key 根据key获取value
     */
    public Object get(String key) {
        Assert.notNull(key, "key is not null");
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @param key 删除key
     */
    public Boolean remove(String key) {
        Assert.notNull(key, "key is not null");
        return redisTemplate.delete(key);
    }



}
