package com.manage.cache.implement;

import com.manage.cache.CacheManager;
import com.manage.cache.bean.LocalCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            runClearTask(localCache);
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

    @Override
    public boolean extendTTL(Object key, long ttl) {
        LocalCache cache = localCache.get(key);
        if (cache == null) {
            return false;
        }
        localCache.put(key, cache.extendTTL(ttl));
        return false;
    }

    private static void cleanCache(Map<Object, LocalCache> localCache) {
        for (Map.Entry<Object, LocalCache> entry : localCache.entrySet()) {
            if (entry.getValue().isExpired()) {
                remove(entry.getKey());
            }
        }
    }

    private static synchronized void remove(Object key) {
        localCache.remove(key);
    }

    private static void runClearTask(Map<Object, LocalCache> localCache) {
        schedule = Executors.newSingleThreadScheduledExecutor();
        schedule.scheduleWithFixedDelay(new ClearTask(localCache), delay, delay, TimeUnit.SECONDS);
    }

    static class ClearTask implements Runnable {

        private Map<Object, LocalCache> localCache;

        public ClearTask(Map<Object, LocalCache> localCache) {
            this.localCache = localCache;
        }

        @Override
        public void run() {
            try {
                cleanCache(localCache);
            } catch (Exception e) {
                LOGGER.error("Clean cache failed", e);
            }
        }
    }

}