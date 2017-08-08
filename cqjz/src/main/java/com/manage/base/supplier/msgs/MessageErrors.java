package com.manage.base.supplier.msgs;

import com.manage.base.interfaces.CoreMsgs;
import com.manage.kernel.spring.comm.Messages;

public enum MessageErrors implements CoreMsgs {

    SYSTEM_ERROR(20000, "system error"),
    CORE_ERROR(20001,"core error"),
    SAVE_FAILED(20002,"core error"),
    DELETE_FAILED(20003,"core error"),

    NOT_FOUND(200001,"not found"),
    MENU_NOT_FOUND(200002,"not found menu"),
    MENU_HAS_CHILDREN(200003, "The menu has sub menus")
    ;

    private int code;
    private String message;

    MessageErrors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getDefaultMessage() {
        return message;
    }

    @Override
    public String getMessage() {
        String businessMsg = Messages.get("message.business.error." + code);
        if (businessMsg != null && !businessMsg.endsWith(String.valueOf(code))) {
            return businessMsg;
        }
        return getDefaultMessage();
    }
}
