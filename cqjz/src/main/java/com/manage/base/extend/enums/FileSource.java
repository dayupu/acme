package com.manage.base.extend.enums;

import com.manage.base.extend.model.DBEnum;

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
