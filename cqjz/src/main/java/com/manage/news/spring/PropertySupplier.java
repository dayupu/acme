package com.manage.news.spring;

public class PropertySupplier {

    private String tokenStrategy;
    private int tokenValidMinutes;


    public String getTokenStrategy() {
        return tokenStrategy;
    }

    public void setTokenStrategy(String tokenStrategy) {
        this.tokenStrategy = tokenStrategy;
    }

    public int getTokenValidMinutes() {
        return tokenValidMinutes;
    }

    public void setTokenValidMinutes(int tokenValidMinutes) {
        this.tokenValidMinutes = tokenValidMinutes;
    }
}
