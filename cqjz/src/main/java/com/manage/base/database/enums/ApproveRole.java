package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;
import com.manage.base.database.model.Localizable;

/**
 * Created by bert on 2017/10/6.
 */
public enum ApproveRole implements DBEnum, Localizable {

    CLERK(100, "resource.approve.group.clerk"),
    TEAM_LEADER(101, "resource.approve.group.teamLeader"),
    SECURE_MEMBER(102, "resource.approve.group.secureMember"),
    CORPS_LEADER(103, "resource.approve.group.corpsLeader");

    private String messageKey;
    private Integer constant;

    ApproveRole(Integer constant, String messageKey) {
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
    public String actGroupId() {
        return String.valueOf(constant);
    }
}
