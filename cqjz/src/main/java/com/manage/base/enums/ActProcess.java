package com.manage.base.enums;

import com.manage.kernel.spring.comm.Messages;

/**
 * Created by bert on 2017/10/4.
 */
public enum ActProcess {
    APPLY("apply", "resource.constant.act.process.apply"),
    AGREE("agree", "resource.constant.act.process.agree"),
    REJECT("reject", "resource.constant.act.process.reject");

    private String action;
    private String messageKey;

    ActProcess(String action, String messageKey) {
        this.action = action;
        this.messageKey = messageKey;
    }

    public String action() {
        return action;
    }

    public String message() {
        return Messages.get(messageKey);
    }
}
