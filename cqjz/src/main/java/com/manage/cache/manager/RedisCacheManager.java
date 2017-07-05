package com.manage.cache.manager;

import com.manage.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class RedisCacheManager implements CacheManager {

    private RedisTemplate redisTemplate;

    public RedisCacheManager(RedisTemplate redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(Object key, Object obj) {
        redisTemplate.opsForValue().set(key, obj);
    }

    @Override
    public void put(Object key, Object obj, long ttl) {
        redisTemplate.opsForValue().set(key, obj, ttl, TimeUnit.SECONDS);
    }

    @Override
    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Integer append(Object key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }
}
