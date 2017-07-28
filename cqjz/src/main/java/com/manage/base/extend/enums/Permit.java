package com.manage.base.extend.enums;

import com.manage.base.atomic.DBMapper;

public enum Permit implements DBMapper<String> {

    TEST("test", "permission.test"),
    GROUP_DEFAULT("default", "permission.group.default", PermitType.GROUP);

    private String code;
    private String resource;
    private PermitType type;

    Permit(String code, String resource) {
        this.code = code;
        this.resource = resource;
        this.type = PermitType.FUNCTION;
    }

    Permit(String code, String resource, PermitType type) {
        this.code = code;
        this.resource = resource;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getResource() {
        return resource;
    }

    public PermitType getType() {
        return type;
    }

    @Override
    public String dbValue() {
        return code;
    }
}