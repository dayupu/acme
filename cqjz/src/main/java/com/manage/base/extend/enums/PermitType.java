package com.manage.base.extend.enums;

import com.manage.base.atomic.DBMapper;

public enum PermitType implements DBMapper<Integer> {

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

    @Override
    public Integer dbValue() {
        return this.constant;
    }
}
