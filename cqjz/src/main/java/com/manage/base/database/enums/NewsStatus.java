package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;
import com.manage.base.database.model.Localizable;

/**
 * Created by bert on 17-9-6.
 */
public enum NewsStatus implements DBEnum, Localizable {

    DRAFT(0, "resource.constant.news.status.draft");

    private int constant;
    private String messageKey;

    NewsStatus(int constant, String messageKey) {
        this.constant = constant;
        this.messageKey = messageKey;
    }

    @Override
    public String messageKey() {
        return messageKey;
    }

    @Override
    public Integer getConstant() {
        return constant;
    }
}
