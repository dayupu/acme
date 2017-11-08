package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;
import com.manage.base.database.model.Localizable;

public enum SimpleStatus implements DBEnum, Localizable {

    ENABLE(1, "resource.constant.data.status.enabled"),
    DISABLE(2, "resource.constant.data.status.disabled");

    private int constant;
    private String messageKey;

    SimpleStatus(int constant, String messageKey) {
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
