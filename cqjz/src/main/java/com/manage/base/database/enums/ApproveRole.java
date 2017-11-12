package com.manage.base.database.enums;

import com.manage.base.database.model.Localizable;
import com.manage.base.database.model.VarDBEnum;

/**
 * Created by bert on 2017/10/6.
 */
public enum ApproveRole implements VarDBEnum, Localizable {

    EMPLOYEE("EP", "resource.approve.group.clerk"),
    CAPTAIN("CP", "resource.approve.group.teamLeader"),
    SECURER("SP", "resource.approve.group.secureMember"),
    LEADER("LP", "resource.approve.group.corpsLeader");

    private String messageKey;
    private String code;

    ApproveRole(String code, String messageKey) {
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

    public ApproveRole nextRole() {
        switch (this) {
            case EMPLOYEE:
                return CAPTAIN;
            case CAPTAIN:
                return SECURER;
            case SECURER:
                return LEADER;
            case LEADER:
                return null;
            default:
                return null;
        }
    }
}
