package com.manage.base.exception;

import com.manage.base.interfaces.CoreMsgs;
import com.manage.base.supplier.msgs.MessageErrors;

public class NotFoundException extends CoreException {

    private static CoreMsgs ERROR = MessageErrors.NOT_FOUND;

    public NotFoundException() {
        this.setCoreMsgs(ERROR);
    }

    public NotFoundException(CoreMsgs coreMsgs) {
        super(coreMsgs);
    }
}
