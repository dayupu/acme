package com.manage.plugins.cache.strategy;

import com.manage.plugins.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

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

    @Override
    public boolean extendTTL(Object key, long ttl) {
        if(get(key) == null){
            return false;
        }
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
        return true;
    }

}
