package com.manage.base.supplier.msgs;

import com.manage.kernel.spring.comm.Messages;

public enum MessageErrors implements CoreMsgs {

    SYSTEM_ERROR(20000, "System Error"),
    CORE_ERROR(20001, "Business Exception"),
    SAVE_FAILED(20002, "Save Fail"),
    DELETE_FAILED(20003, "Delete Fail"),
    UPLOAD_FAILED(20004, "Upload Fail"),
    ACT_NOT_SUPPORT(20005, "Not support"),
    PRIVILEGE_DENIED(20010, "privilege denied"),
    NOT_FOUND(200001, "Not found"),
    MENU_NOT_FOUND(200002, "Not found the menu"),
    MENU_HAS_CHILDREN(200003, "The menu has sub menus"),
    USER_NOT_FOUND(200010, "NOT found the user"),
    USER_IS_EXISTS(200011, "The user is exists"),
    ROLE_NOT_FOUND(200020, "NOT found the role"),
    ORGAN_NOT_FOUND(200040, "NOT found the organization"),
    ORGAN_HAS_CHILDREN(200041, "The organization has sub organization"),
    ORGAN_CODE_ERROR(200042, "The organization code error"),
    WATCH_TIME_ERROR(200050, "The watchTime less than watchTimeEnd"),
    WATCH_TIME_RANGE_INVALID(200051, "watch time range greater than 30"),
    SUPERSTAR_NOT_FOUND(200060, "NOT found the superstar"),
    PASSWORD_ERROR(200070, "Password error"),
    STYLE_NOT_FOUND(200080, "NOT found the style"),
    NEWS_NOT_FOUND(200100, "NOT found the news"),
    NEWS_TOPIC_NOT_FOUND(200101, "NOT found the news topic"),
    NEWS_IMAGE_NOT_UPLOAD(200102, "News image not upload"),
    NEWS_TYPE_NOT_SUPPORT(200103, "News type not support");

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
