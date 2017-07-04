package com.manage.base.enums;

public enum Privilege {

    TEST("test", "permission.test");

    private String code;
    private String resourceKey;

    Privilege(String code, String resourceKey) {
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