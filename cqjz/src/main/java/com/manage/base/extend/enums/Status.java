package com.manage.base.extend.enums;

import com.manage.base.extend.define.DBEnum;
import com.manage.base.extend.define.Localisable;

public enum Status implements DBEnum, Localisable {

    INIT(0, "resource.constant.status.init"),
    AVALID(1, "resource.constant.status.avalid"),
    INVALID(2, "resource.constant.status.invalid"),
    DELETE(3, "resource.constant.status.deleted"),
    RETRY(5, "resource.constant.status.retry"),
    EXPIRE(6, "resource.constant.status.expired"),
    FAILURE(7, "resource.constant.status.failed"),
    SUCCESS(9, "resource.constant.status.success");

    private int constant;
    private String messageKey;

    Status(int constant, String messageKey) {
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
