package com.manage.base.extend.enums;

import com.manage.base.interfaces.DBMapper;

public enum FlowStatus implements DBMapper<Integer> {

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


    public Integer getConstant() {
        return constant;
    }

    public void setConstant(Integer constant) {
        this.constant = constant;
    }

    @Override
    public Integer dbValue() {
        return this.constant;
    }

}
