package com.manage.cache;

import com.manage.cache.manager.LocalCacheManager;
import com.manage.cache.manager.RedisCacheManager;
import java.util.HashMap;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.redis.core.RedisTemplate;

public class CacheFactoryBean implements FactoryBean<CacheManager> {

    private static final String STRATEGY_REDIS = "redis";
    private static final String STRATEGY_DEFAULT = "local";

    private static final HashMap localCache = new HashMap();

    private String strategy = STRATEGY_DEFAULT;

    private RedisTemplate redisTemplate;

    private CacheManager cacheManager;

    @Override
    public CacheManager getObject() throws Exception {
        if (STRATEGY_REDIS.equals(strategy)) {
            cacheManager = new RedisCacheManager(redisTemplate);
        } else {
            cacheManager = new LocalCacheManager(localCache);
        }
        return cacheManager;
    }

    @Override
    public Class<?> getObjectType() {
        return CacheManager.class;
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
}
