package com.manage.cache;

import com.manage.cache.implement.LocalCacheManager;
import com.manage.cache.implement.RedisCacheManager;

import java.util.HashMap;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.redis.core.RedisTemplate;

public class CacheFactoryBean implements FactoryBean<CacheFactoryBean> {

    private static final String STRATEGY_REDIS = "redis";
    private static final String STRATEGY_DEFAULT = "local";

    private static final HashMap localCache = new HashMap();

    private String strategy = STRATEGY_DEFAULT;

    private RedisTemplate redisTemplate;

    private CacheManager cacheManager;

    @Override
    public CacheFactoryBean getObject() throws Exception {

        if (STRATEGY_REDIS.equals(strategy)) {
            cacheManager = new RedisCacheManager(redisTemplate);
        } else {
            cacheManager = new LocalCacheManager(localCache);
        }
        return this;
    }

    @Override
    public Class<?> getObjectType() {
        return CacheFactoryBean.class;
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
