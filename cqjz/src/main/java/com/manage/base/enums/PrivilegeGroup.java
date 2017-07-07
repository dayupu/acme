package com.manage.base.enums;

public enum PrivilegeGroup {

    DEFAULT("default", "permission.group.default"),
    USER("group.user","permission.group.user");

    private String code;
    private String resourceKey;

    PrivilegeGroup(String code, String resourceKey) {
        this.code = code;
        this.resourceKey = resourceKey;
    }

    public String getCode() {
        return code;
    }

    public String getResourceKey() {
        return resourceKey;
    }
}