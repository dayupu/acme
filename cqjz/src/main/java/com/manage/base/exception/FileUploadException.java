package com.manage.base.exception;

import com.manage.base.supplier.msgs.CoreMsgs;
import com.manage.base.supplier.msgs.MessageErrors;

public class FileUploadException extends CoreException {

    private static CoreMsgs ERROR = MessageErrors.UPLOAD_FAILED;

    public FileUploadException() {
        this.setCoreMsgs(ERROR);
    }

    public FileUploadException(CoreMsgs coreMsgs) {
        super(coreMsgs);
    }
}
