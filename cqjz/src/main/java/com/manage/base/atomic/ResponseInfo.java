package com.manage.base.atomic;

import com.manage.base.extend.enums.ResponseStatus;

public class ResponseInfo<T> {

    public ResponseStatus status;
    public String message;
    public T content;

    public ResponseInfo() {

    }

    public ResponseInfo(ResponseStatus status) {
        this.status = status;
    }

    public ResponseInfo(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseInfo(ResponseStatus status, String message, T content) {
        this.status = status;
        this.message = message;
        this.content = content;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
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
