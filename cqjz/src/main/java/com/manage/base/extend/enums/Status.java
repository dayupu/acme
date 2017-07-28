package com.manage.base.extend.enums;

import com.manage.base.atomic.DBMapper;

public enum Status implements DBMapper<Integer> {

    INIT(0, "Init"),
    AVALID(1, "Avalid"),
    INVALID(2, "Invalid"),
    DELETE(3, "Deleted"),
    RETRY(5, "Retry"),
    EXPIRE(6, "Expired"),
    FAILURE(7, "Failed"),
    SUCCESS(9, "Success");

    private int constant;
    private String message;

    Status(int constant, String message) {
        this.constant = constant;
        this.message = message;
    }

    @Override
    public Integer dbValue() {
        return this.constant;
    }
}
