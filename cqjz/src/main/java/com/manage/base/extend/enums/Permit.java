package com.manage.base.extend.enums;

import com.manage.base.extend.define.Localisable;
import com.manage.base.extend.define.VarDBEnum;

public enum Permit implements VarDBEnum, Localisable {

    // FUNCTION
    CREATE("01", "resource.permission.function.create"),
    UPDATE("02", "resource.permission.function.update"),
    DELETE("03", "resource.permission.function.delete"),

    // GROUP
    GROUP_DEFAULT("99", "resource.permission.group.default", PermitType.GROUP),
    GROUP_ROLE("10", "resource.permission.group.role", PermitType.GROUP);

    private String code;
    private String messageKey;
    private PermitType type;

    Permit(String code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
        this.type = PermitType.FUNCTION;
    }

    Permit(String code, String messageKey, PermitType type) {
        this.code = code;
        this.messageKey = messageKey;
        this.type = type;
    }

    public PermitType getType() {
        return type;
    }

    @Override
    public String messageKey() {
        return messageKey;
    }

    @Override
    public String getCode() {
        return code;
    }
}