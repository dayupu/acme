package com.manage.news.token.cache;

import com.manage.news.token.base.Token;
import com.manage.news.token.base.TokenManager;
import com.manage.news.token.exception.TokenNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokenCacheManager implements TokenManager {
    private static final Logger LOGGER = LogManager.getLogger(TokenCacheManager.class);
    private static Map<String, CacheToken> cacheMap = new ConcurrentHashMap<String, CacheToken>();
    private static TokenCacheManager tokenCacheManager = new TokenCacheManager();
    private static ScheduledExecutorService schedule;

    private TokenCacheManager() {
    }

    public static TokenCacheManager getInstance() {
        if (schedule == null) {
            runClearTask();
        }
        return tokenCacheManager;
    }

    private static void runClearTask() {
        schedule = Executors.newSingleThreadScheduledExecutor();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    LOGGER.info("Clear expired tokens.");
                    TokenCacheManager.getInstance().clearExpiredTokens();
                } catch (Exception e) {
                    LOGGER.error(e);
                }
            }
        };
        schedule.scheduleWithFixedDelay(task, 60, 300, TimeUnit.SECONDS);
    }

    @Override
    public boolean isValid(String tokenId) {

        CacheToken token = cacheMap.get(tokenId);
        if (token == null) {
            return false;
        }
        if (token.isExpired()) {
            return false;
        }
        return true;
    }

    @Override
    public synchronized void register(Token token, long seconds) {

        CacheToken cacheToken = new CacheToken(token);
        cacheToken.extendTTL(seconds);
        cacheMap.put(token.getId(), cacheToken);
    }

    @Override
    public void extendTTL(String tokenId, long seconds) {
        CacheToken token = cacheMap.get(tokenId);
        if (token == null) {
            throw new TokenNotFoundException();
        }

        token.extendTTL(seconds);
        update(token);
    }

    public void clearExpiredTokens() {
        for (Map.Entry<String, CacheToken> entry : cacheMap.entrySet()) {
            if (entry.getValue().isExpired()) {
                cacheMap.remove(entry.getKey());
            }
        }
    }

    private synchronized boolean update(CacheToken token) {
        if (!cacheMap.containsKey(token.getId())) {
            throw new TokenNotFoundException();
        }
        cacheMap.put(token.getId(), token);
        return true;
    }
}
