package com.manage.base.extend.enums;

import com.manage.base.extend.define.DBMapper;
import com.manage.base.extend.define.SourceMessage;

/**
 * Created by bert on 2017/8/13.
 */
public enum Gender implements DBMapper, SourceMessage {

    MALE(1, "resource.constant.gender.male"),
    FEMALE(0, "resource.constant.gender.female");;

    private int constant;
    private String messageKey;

    Gender(int constant, String messageKey) {
        this.constant = constant;
        this.messageKey = messageKey;
    }

    @Override
    public String messageKey() {
        return messageKey;
    }


    @Override
    public Integer dbValue() {
        return constant;
    }

}
