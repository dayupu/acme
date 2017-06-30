package com.manage.news.base.enums;

public enum ProcessState {

    INIT(0, "init"),
    SUBMIT(1, "submit"),
    IN_APPROVAL(2, "in approval"),
    APPROVE(3, "approve"),
    REJECT(4, "reject"),
    CANCEL(5, "cancel");

    private Integer constant;
    private String message;

    ProcessState(Integer constant, String message) {

        this.constant = constant;
        this.message = message;
    }
}
