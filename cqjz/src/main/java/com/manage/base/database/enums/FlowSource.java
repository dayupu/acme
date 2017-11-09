package com.manage.base.database.enums;

import com.manage.base.database.model.Localizable;
import com.manage.base.database.model.VarDBEnum;

/**
 * Created by bert on 2017/10/4.
 */
public enum FlowSource implements VarDBEnum, Localizable {

    NEWS("NEWS", "resource.approve.type.news");

    private String code;
    private String messageKey;

    FlowSource(String code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    @Override
    public String messageKey() {
        return messageKey;
    }

    @Override
    public String getCode() {
        return code;
    }
}
