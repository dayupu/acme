package com.manage.base.exception;


import com.manage.base.extend.enums.ApiMessage;

public class ApiExeception extends Exception {

    private ApiMessage apiMessage = ApiMessage.SERVER_ERROR;

    public ApiExeception() {
        super();
    }

    public ApiExeception(String message) {
        super(message);
    }

    public ApiExeception(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiExeception(Throwable cause) {
        super(cause);
    }

    public ApiMessage getApiMessage() {
        return apiMessage;
    }

    public void setApiMessage(ApiMessage apiMessage) {
        this.apiMessage = apiMessage;
    }
}
