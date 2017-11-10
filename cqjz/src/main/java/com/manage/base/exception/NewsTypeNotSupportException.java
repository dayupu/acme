package com.manage.base.exception;

import com.manage.base.supplier.msgs.CoreMsgs;
import com.manage.base.supplier.msgs.MessageErrors;

public class NewsTypeNotSupportException extends CoreException {

    private static CoreMsgs ERROR = MessageErrors.NEWS_IMAGE_NOT_UPLOAD;

    public NewsTypeNotSupportException() {
        this.setCoreMsgs(ERROR);
    }

    public NewsTypeNotSupportException(CoreMsgs coreMsgs) {
        super(coreMsgs);
    }
}
