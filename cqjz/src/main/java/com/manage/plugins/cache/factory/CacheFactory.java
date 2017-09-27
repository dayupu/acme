package com.manage.plugins.cache.factory;

import com.manage.plugins.cache.CacheManager;
import com.manage.plugins.cache.strategy.LocalCacheManager;
import com.manage.plugins.cache.strategy.RedisCacheManager;

import java.util.HashMap;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.redis.core.RedisTemplate;

public class CacheFactory implements FactoryBean<CacheFactory> {

    private static final String STRATEGY_REDIS = "redis";
    private static final String STRATEGY_DEFAULT = "local";

    private static final HashMap localCache = new HashMap();

    private String strategy = STRATEGY_DEFAULT;

    private RedisTemplate redisTemplate;

    private CacheManager cacheManager;

    @Override
    public CacheFactory getObject() throws Exception {

        if (STRATEGY_REDIS.equals(strategy)) {
            cacheManager = new RedisCacheManager(redisTemplate);
        } else {
            cacheManager = new LocalCacheManager(localCache);
        }
        return this;
    }

    @Override
    public Class<?> getObjectType() {
        return CacheFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }
}
