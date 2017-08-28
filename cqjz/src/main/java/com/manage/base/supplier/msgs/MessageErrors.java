package com.manage.base.supplier.msgs;

import com.manage.base.interfaces.CoreMsgs;
import com.manage.kernel.spring.comm.Messages;

public enum MessageErrors implements CoreMsgs {

    SYSTEM_ERROR(20000, "System Error"),
    CORE_ERROR(20001, "Business Exception"),
    SAVE_FAILED(20002, "Save Fail"),
    DELETE_FAILED(20003, "Delete Fail"),
    UPLOAD_FAILED(20004, "Upload Fail"),
    NOT_FOUND(200001, "Not found"),
    MENU_NOT_FOUND(200002, "Not found the menu"),
    MENU_HAS_CHILDREN(200003, "The menu has sub menus"),
    USER_NOT_FOUND(200010, "NOT found the user"),
    USER_IS_EXISTS(200011, "The user is exists"),
    ROLE_NOT_FOUND(200020, "NOT found the role"),
    DEPART_NOT_FOUND(200030, "NOT found the department"),
    DEPART_HAS_CHILDREN(200031, "The department has sub deparments");

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
