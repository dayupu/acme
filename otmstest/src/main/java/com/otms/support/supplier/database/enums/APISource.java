package com.otms.support.supplier.database.enums;

import com.otms.support.supplier.database.define.DBEnum;

/**
 * Created by bert on 17-9-11.
 */
public enum APISource implements DBEnum {

    ORDER_EVENT(1),
    JOB_SHEET_EVENT(1),
    BILLING_EVENT(1),
    CONFIRM_DISCREPANCY_EVENT(1);

    private Integer constant;

    APISource(Integer constant) {
        this.constant = constant;
    }

    @Override
    public Integer getConstant() {
        return constant;
    }
}
