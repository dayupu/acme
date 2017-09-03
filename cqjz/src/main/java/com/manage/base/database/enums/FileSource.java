package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;

/**
 * Created by bert on 17-8-25.
 */
public enum FileSource implements DBEnum {

    NEWS(10, "news");

    private Integer constant;
    private String dir;

    FileSource(Integer constant, String dir) {
        this.constant = constant;
        this.dir = dir;
    }

    public String getDir() {
        return dir;
    }

    @Override
    public Integer getConstant() {
        return constant;
    }
}
