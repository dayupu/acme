package com.manage.news.token.cache;

import com.manage.news.token.base.Token;
import org.springframework.util.Assert;
import org.springframework.util.SystemPropertyUtils;

import java.io.Serializable;

/**
 * Created by bert on 2017/7/2.
 */
public class CacheToken extends Token implements Serializable {

    private static final long serialVersionUID = 2829264921465108202L;

    private long expireMillis = System.currentTimeMillis();


    public CacheToken(Token token) {
        setId(token.getId());
        setIp(token.getIp());
        setUser(token.getUser());
    }

    public boolean isExpired() {
        if (expireMillis < currentMills()) {
            return true;
        }
        return false;
    }

    public void extendTTL(long seconds) {
        this.expireMillis = currentMills() + (seconds * 1000);
    }

    private long currentMills() {
        return System.currentTimeMillis();
    }
}
