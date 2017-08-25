package com.manage.base.extend.enums;

import com.manage.base.extend.model.DBEnum;

public enum FlowStatus implements DBEnum {

    INIT(0, "init"),
    SUBMIT(1, "submit"),
    IN_APPROVAL(2, "in approval"),
    APPROVE(3, "approve"),
    REJECT(4, "reject"),
    CANCEL(5, "cancel");

    private Integer constant;
    private String message;

    FlowStatus(Integer constant, String message) {
        this.constant = constant;
        this.message = message;
    }

    @Override
    public Integer getConstant() {
        return constant;
    }

    public void setConstant(Integer constant) {
        this.constant = constant;
    }
}
