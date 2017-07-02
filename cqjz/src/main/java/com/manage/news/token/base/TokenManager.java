package com.manage.news.token.base;


public interface TokenManager {

    void register(Token token, long TTL);

    void extendTTL(String tokenId, long seconds);

    boolean isValid(String tokenId);
}
