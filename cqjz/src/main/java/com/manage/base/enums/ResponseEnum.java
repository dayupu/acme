package com.manage.base.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;

public enum ResponseEnum implements Serializable {

    SUCCESS(1000),
    ERROR(2000);

    private int code;

    ResponseEnum(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
