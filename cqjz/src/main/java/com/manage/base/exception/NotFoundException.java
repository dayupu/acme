package com.manage.base.exception;

import com.manage.base.interfaces.CoreMsgs;

public class NotFoundException extends CoreException {

    private static CoreMsgs ERROR;

    public NotFoundException() {
        this.setCoreMsgs(ERROR);
    }

    public NotFoundException(String message) {
        super(message);
        this.setCoreMsgs(ERROR);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.setCoreMsgs(ERROR);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
        this.setCoreMsgs(ERROR);
    }

    public NotFoundException(CoreMsgs coreMsgs) {
        super(coreMsgs);
    }
}
