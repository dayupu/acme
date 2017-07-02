package com.manage.news.token.base;

import org.springframework.util.Assert;

public class Token {

    private String id;
    private String ip;
    private TokenUser user;

    public Token(){

    }

    public Token(String id, String ip, TokenUser user){
        Assert.notNull(id);
        this.id = id;
        this.ip = ip;
        this.user = user;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public TokenUser getUser() {
        return user;
    }

    public void setUser(TokenUser user) {
        this.user = user;
    }
}
