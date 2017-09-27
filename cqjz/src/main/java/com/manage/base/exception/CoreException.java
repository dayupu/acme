package com.manage.base.exception;

import com.manage.base.supplier.msgs.CoreMsgs;
import com.manage.base.supplier.msgs.MessageErrors;

public class CoreException extends RuntimeException {

    private CoreMsgs coreMsgs = MessageErrors.CORE_ERROR;

    private String message;

    public CoreException() {
    }

    public CoreException(CoreMsgs coreMsgs) {
        setCoreMsgs(coreMsgs);
    }

    public CoreException(String message) {
        super(message);
        this.message = message;
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public CoreException(Throwable cause) {
        super(cause);
    }

    public CoreMsgs getCoreMsgs() {
        return coreMsgs;
    }

    public void setCoreMsgs(CoreMsgs coreMsgs) {
        this.coreMsgs = coreMsgs;
    }

    @Override
    public String getMessage() {

        if (message != null) {
            return message;
        }

        if (getCoreMsgs() != null) {
            return getCoreMsgs().getMessage();
        }

        return super.getMessage();
    }
}
