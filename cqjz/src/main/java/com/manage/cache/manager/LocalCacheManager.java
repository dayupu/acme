package com.manage.cache.manager;

import com.manage.cache.CacheManager;
import com.manage.cache.base.LocalCache;
import com.manage.cache.impl.local.CacheToken;
import com.manage.cache.impl.local.TokenCacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LocalCacheManager implements CacheManager {

    private static final Logger LOGGER = LogManager.getLogger(LocalCacheManager.class);

    private static Map<Object, LocalCache> localCache;

    private static ScheduledExecutorService schedule;

    private static final long delay = 300;

    public LocalCacheManager(Map localCache) {
        this.localCache = localCache;
        if (schedule == null) {
            runClearTask();
        }
    }

    @Override
    public synchronized void put(Object key, Object obj) {
        localCache.put(key, new LocalCache(obj));
    }

    @Override
    public synchronized void put(Object key, Object obj, long ttl) {
        localCache.put(key, new LocalCache(obj, ttl));
    }

    @Override
    public Object get(Object key) {
        LocalCache cache = localCache.get(key);
        if (cache == null) {
            return null;
        }
        if (cache.isExpired()) {
            remove(key);
            return null;
        }
        return cache.value();
    }

    @Override
    public Integer append(Object key, String str) {
        LocalCache cache = localCache.get(key);
        String value = cache == null ? str : cache.value() + str;
        localCache.put(key, new LocalCache(value));
        return value.length();
    }

    private static void cleanCache() {
        for (Map.Entry<Object, LocalCache> entry : localCache.entrySet()) {
            if (entry.getValue().isExpired()) {
                remove(entry.getKey());
            }
        }
    }

    private static synchronized void remove(Object key) {
        localCache.remove(key);
    }

    private static void runClearTask() {
        schedule = Executors.newSingleThreadScheduledExecutor();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    cleanCache();
                } catch (Exception e) {
                    LOGGER.error("Clean cache failed", e);
                }
            }
        };
        schedule.scheduleWithFixedDelay(task, delay, delay, TimeUnit.SECONDS);
    }
}
