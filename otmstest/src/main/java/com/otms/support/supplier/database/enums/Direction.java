package com.otms.support.supplier.database.enums;

import com.otms.support.supplier.database.define.DBEnum;

public enum Direction implements DBEnum {

    IN(1),
    OUT(0);

    private Integer constant;

    Direction(Integer constant) {
        this.constant = constant;
    }

    @Override
    public Integer getConstant() {
        return constant;
    }
}
