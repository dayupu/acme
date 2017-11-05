package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;

/**
 * Created by bert on 17-8-25.
 */
public enum FileSource implements DBEnum {

    NEWS_SUMMARY(10, "news summary"),
    NEWS_BODY(11, "news body"),
    UEDITOR(12, "ueditor"),
    JZ_STYLE(13, "jz style");

    private Integer constant;
    private String message;

    FileSource(Integer constant, String message) {
        this.constant = constant;
        this.message = message;
    }

    public String getDir() {
        return message;
    }

    @Override
    public Integer getConstant() {
        return constant;
    }
}
