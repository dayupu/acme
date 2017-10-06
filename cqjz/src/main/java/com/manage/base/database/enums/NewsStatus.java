package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;
import com.manage.base.database.model.Localizable;

/**
 * Created by bert on 17-9-6.
 */
public enum NewsStatus implements DBEnum, Localizable {

    DRAFT(1, "resource.constant.news.status.draft"),
    SUBMIT(2, "resource.constant.news.status.submit"),
    APPROVE(3, "resource.constant.news.status.approve"),
    REJECT(4, "resource.constant.news.status.reject"),
    PASS(5, "resource.constant.news.status.pass"),
    DELETE(0, "resource.constant.news.status.delete");

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

    public boolean canEdit() {
        return this == DRAFT || this == REJECT;
    }
}
