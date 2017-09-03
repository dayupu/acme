package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;
import com.manage.base.database.model.Localisable;

/**
 * Created by bert on 17-8-25.
 */
public enum FileType implements DBEnum, Localisable {

    IMAGE(1, "news.image");

    private Integer constant;
    private String messageKey;

    FileType(Integer constant, String messageKey) {
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
