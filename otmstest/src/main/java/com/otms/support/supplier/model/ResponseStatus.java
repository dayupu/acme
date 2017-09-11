package com.otms.support.supplier.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum ResponseStatus implements Serializable {

    SUCCESS(1000),
    FAIL(2000),
    ERROR(3000);

    private int code;

    ResponseStatus(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
