package com.manage.base.act;

/**
 * Created by bert on 2017/10/4.
 */
public enum ActSource {

    NEWS("NEWS");

    private String key;

    ActSource(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
