package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;
import com.manage.base.database.model.Localizable;

/**
 * Created by bert on 2017/10/4.
 */
public enum ActProcess implements DBEnum, Localizable {

    APPLY(1, "resource.constant.act.process.apply", "apply"),
    AGREE(2, "resource.constant.act.process.agree", "agree"),
    REJECT(3, "resource.constant.act.process.reject", "reject"),
    CANCEL(4, "resource.constant.act.process.cancel", "cancel");

    private int constant;
    private String action;
    private String messageKey;

    ActProcess(int constant, String messageKey, String action) {
        this.constant = constant;
        this.action = action;
        this.messageKey = messageKey;
    }


    public static ActProcess toEnumByAction(String action) {
        for (ActProcess actProcess : ActProcess.values()) {
            if (actProcess.action().equals(action)) {
                return actProcess;
            }
        }
        return null;
    }

    public boolean isReject() {
        return this == REJECT;
    }

    public String action() {
        return action;
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
