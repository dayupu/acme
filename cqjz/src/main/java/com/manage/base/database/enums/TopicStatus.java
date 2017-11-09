package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;
import com.manage.base.database.model.Localizable;

public enum TopicStatus implements DBEnum, Localizable {

    ENABLED(1, "resource.constant.data.topic.status.enabled"),
    CLOSED(2, "resource.constant.data.topic.status.closed");

    private int constant;
    private String messageKey;

    TopicStatus(int constant, String messageKey) {
        this.constant = constant;
        this.messageKey = messageKey;
    }

    @Override
    public Integer getConstant() {
        return this.constant;
    }

    @Override
    public String messageKey() {
        return messageKey;
    }
}
