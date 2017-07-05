package com.manage.cache.base;

public interface TokenManager {

    void register(Token token, long TTL);

    void extendTTL(String tokenId, long seconds);

    boolean isValid(String tokenId, String ip);

    Token acquireToken(String tokenId);
}
