package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;

public enum PermitType implements DBEnum {

    GROUP(0, "group"),
    FUNCTION(1, "function");

    private Integer constant;
    private String message;

    PermitType(Integer constant, String message) {
        this.constant = constant;
        this.message = message;
    }

    public Integer getConstant() {
        return constant;
    }

    public String getMessage() {
        return message;
    }

}
