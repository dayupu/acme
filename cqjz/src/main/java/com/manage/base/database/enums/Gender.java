package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;
import com.manage.base.database.model.Localizable;

/**
 * Created by bert on 2017/8/13.
 */
public enum Gender implements DBEnum, Localizable {

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
    public Integer getConstant() {
        return constant;
    }

}
