package com.manage.base.supplier.msgs;

import com.manage.base.interfaces.CoreMsgs;
import com.manage.kernel.spring.comm.Messages;

public enum MessageErrors implements CoreMsgs {

    SYSTEM_ERROR(20000, "System error"),
    CORE_ERROR(20001, "Business exception"),
    SAVE_FAILED(20002, "Save failed"),
    DELETE_FAILED(20003, "Delete failed"),

    NOT_FOUND(200001, "Not found"),
    MENU_NOT_FOUND(200002, "Not found the menu"),
    MENU_HAS_CHILDREN(200003, "The menu has sub menus"),
    USER_NOT_FOUND(200010, "NOT found the user"),
    USER_HAS_EXISTS(200011, "The user has exists"),
    ROLE_NOT_FOUND(200020, "NOT found the role");

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
