package com.manage.base.extend.enums;

public enum ApiMessage {

    SERVER_ERROR("10000", "server error"),
    ACCESS_DENIED("10001", "access denied");

    private String code;
    private String message;

    ApiMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
