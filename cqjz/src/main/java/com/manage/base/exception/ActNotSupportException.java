package com.manage.base.exception;

import com.manage.base.supplier.msgs.CoreMsgs;
import com.manage.base.supplier.msgs.MessageErrors;

public class ActNotSupportException extends CoreException {

    private static CoreMsgs ERROR = MessageErrors.ACT_NOT_SUPPORT;

    public ActNotSupportException() {
        this.setCoreMsgs(ERROR);
    }

    public ActNotSupportException(CoreMsgs coreMsgs) {
        super(coreMsgs);
    }
}
