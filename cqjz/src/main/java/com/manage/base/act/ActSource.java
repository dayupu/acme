package com.manage.base.act;

import com.manage.base.database.model.Localizable;

/**
 * Created by bert on 2017/10/4.
 */
public enum ActSource implements Localizable {

    NEWS("NEWS", "resource.approve.type.news");

    private String key;
    private String messageKey;


    ActSource(String key, String messageKey) {
        this.key = key;
        this.messageKey = messageKey;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String messageKey() {
        return messageKey;
    }
}
