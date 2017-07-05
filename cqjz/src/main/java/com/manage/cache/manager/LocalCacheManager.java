package com.manage.cache.manager;

import com.manage.cache.CacheManager;
import java.util.Map;

public class LocalCacheManager implements CacheManager {

    private Map localCache;

    public LocalCacheManager(Map localCache) {
        this.localCache = localCache;
    }
}
