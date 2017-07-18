package com.manage.base.bean;

import com.manage.base.enums.ApiMessage;
import com.manage.base.enums.ResponseEnum;

/**
 * Created by bert on 2017/7/2.
 */
public class ResponseInfo<T> {

    public ResponseEnum status;
    public String message;
    public T content;

    public ResponseInfo() {

    }

    public ResponseInfo(ResponseEnum status) {
        this.status = status;
    }

    public ResponseInfo(ResponseEnum status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseInfo(ResponseEnum status, String message, T content) {
        this.status = status;
        this.message = message;
        this.content = content;
    }

    public ResponseEnum getStatus() {
        return status;
    }

    public void setStatus(ResponseEnum status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}