package com.manage.base.bean;

import com.manage.base.enums.ApiMessage;

/**
 * Created by bert on 2017/7/2.
 */
public class ResponseEntity {

    private String code = "0";
    private String message;
    private Object data;

    public ResponseEntity() {

    }

    public ResponseEntity(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseEntity(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseEntity(ApiMessage apiMessage) {
        this.code = apiMessage.getCode();
        this.message = apiMessage.getMessage();
    }

    public ResponseEntity(ApiMessage apiMessage, Object data) {
        this.code = apiMessage.getCode();
        this.message = apiMessage.getMessage();
        this.data = data;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
