package com.manage.kernel.test;

/**
 * Created by bert on 17-8-23.
 */
public class EventResponse {

    private int statusCode;
    private String entityStr;

    public EventResponse() {

    }

    public EventResponse(int statusCode, String entityStr) {
        this.statusCode = statusCode;
        this.entityStr = entityStr;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getEntityStr() {
        return entityStr;
    }

    public void setEntityStr(String entityStr) {
        this.entityStr = entityStr;
    }

    public boolean isSuccess() {
        return statusCode == 200;
    }
}
