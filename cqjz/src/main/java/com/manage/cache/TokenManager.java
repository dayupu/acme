package com.manage.cache;

import com.manage.base.utils.CryptoUtils;
import com.manage.cache.bean.TokenUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TokenManager {

    private static final Logger LOGGER = LogManager.getLogger(TokenManager.class);

    private static final String TOKEN_PREFIX = "TOKEN-";

    private CacheManager<String, TokenUser> cacheManager;

    private long ttlSeconds;

    public boolean isValid(String tokenId, String ip) {

        TokenUser tokenUser = cacheManager.get(tokenId);
        if (tokenUser == null) {
            return false;
        }
        if (tokenUser.getIp() != null && !ip.equals(tokenUser.getIp())) {
            return false;
        }
        return true;
    }

    public String register(TokenUser user) {
        String tokenId = TOKEN_PREFIX + CryptoUtils.newRandomId();
        cacheManager.put(tokenId, user, ttlSeconds);
        return tokenId;
    }

    public void extendTTL(String tokenId) {
        cacheManager.extendTTL(tokenId, ttlSeconds);
    }

    public TokenUser getTokenUser(String tokenId) {
        return cacheManager.get(tokenId);
    }

    public void setTtlSeconds(long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    public void setCacheManager(CacheManager<String, TokenUser> cacheManager) {
        this.cacheManager = cacheManager;
    }
}
