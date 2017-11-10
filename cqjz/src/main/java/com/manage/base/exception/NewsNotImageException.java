package com.manage.base.exception;

import com.manage.base.supplier.msgs.CoreMsgs;
import com.manage.base.supplier.msgs.MessageErrors;

public class NewsNotImageException extends CoreException {

    private static CoreMsgs ERROR = MessageErrors.NEWS_IMAGE_NOT_UPLOAD;

    public NewsNotImageException() {
        this.setCoreMsgs(ERROR);
    }

    public NewsNotImageException(CoreMsgs coreMsgs) {
        super(coreMsgs);
    }
}
