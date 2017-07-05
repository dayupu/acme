package com.manage.cache.manager;

import com.manage.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCacheManager implements CacheManager {

    private RedisTemplate redisTemplate;

    public RedisCacheManager(RedisTemplate redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

}
