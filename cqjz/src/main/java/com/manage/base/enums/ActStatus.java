package com.manage.base.enums;

import com.manage.base.database.model.Localizable;

/**
 * Created by bert on 17-10-9.
 */
public enum ActStatus implements Localizable {

    PENDING("resource.constant.act.status.pending"),
    COMPLETE("resource.constant.act.status.complete");

    private String messageKey;

    ActStatus(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String messageKey() {
        return messageKey;
    }
}
