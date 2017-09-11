package com.otms.support.supplier.model;


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

    public void wrapSuccess(T content) {
        wrapSuccess(content, (String) null);
    }


    public void wrapSuccess(T content, String message) {
        this.content = content;
        this.message = message;
        this.status = ResponseStatus.SUCCESS;
    }

    public void wrapFail(String message) {
        wrapFail(null, message);
    }


    public void wrapFail(T content, String message) {
        this.content = content;
        this.message = message;
        this.status = ResponseStatus.FAIL;
    }

    public void wrapError(String message) {
        this.message = message;
        this.status = ResponseStatus.ERROR;
    }
}
