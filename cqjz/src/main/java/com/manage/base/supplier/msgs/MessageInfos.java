package com.manage.base.supplier.msgs;

import com.manage.kernel.spring.comm.Messages;

public enum MessageInfos implements CoreMsgs {

    SAVE_SUCCESS(10000, "upload success"),
    DELETE_SUCCESS(10001, "delete success"),
    SUBMIT_SUCCESS(10002, "submit success");

    private int code;
    private String message;

    MessageInfos(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getDefaultMessage() {
        return message;
    }

    @Override
    public String getMessage() {
        String businessMsg = Messages.get("message.business.info." + code);
        if (businessMsg != null && !businessMsg.endsWith(String.valueOf(code))) {
            return businessMsg;
        }
        return getDefaultMessage();
    }
}
