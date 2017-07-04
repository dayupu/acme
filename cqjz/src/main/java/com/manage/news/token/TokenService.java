package com.manage.news.token;

import com.manage.base.utils.CryptoUtils;
import com.manage.news.spring.PropertySupplier;
import com.manage.news.token.base.Token;
import com.manage.news.token.base.TokenManager;
import com.manage.news.token.base.TokenUser;
import com.manage.news.token.cache.TokenCacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

public class TokenService implements InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(TokenService.class);
    private static final String REDIS = "redis";

    private PropertySupplier propertySupplier;

    private TokenManager tokenManager;

    private long ttlSeconds;

    public boolean isValid(String tokenId, String ip) {
        return tokenManager.isValid(tokenId, ip);
    }

    public String register(TokenUser user, String ip) {
        String tokenId = CryptoUtils.newRandomId();
        Token token = new Token(tokenId, ip, user);
        tokenManager.register(token, ttlSeconds);
        return tokenId;
    }

    public void extendTTL(String tokenId) {
        tokenManager.extendTTL(tokenId, ttlSeconds);
    }

    public Token acquireToken(String tokenId) {
        return tokenManager.acquireToken(tokenId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildTokenManager();
        ttlSeconds = propertySupplier.getTokenValidMinutes() * 60;
    }

    private void buildTokenManager() {
        LOGGER.info("Token strategy is {}", propertySupplier.getTokenStrategy());
        if (REDIS.equals(propertySupplier.getTokenStrategy())) {
            //TODO
            return;
        }
        tokenManager = TokenCacheManager.getInstance();
    }

    public void setPropertySupplier(PropertySupplier propertySupplier) {
        this.propertySupplier = propertySupplier;
    }
}
