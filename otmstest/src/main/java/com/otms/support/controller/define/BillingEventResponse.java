package com.otms.support.controller.define;

/**
 * Created by faos on 16-11-21.
 */
public class BillingEventResponse {

    public static final String SUCCESS = "S";
    public static final String CHECKERROR = "E";
    public static final String OTHER = "O";

    private String type;
    private String msg;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
