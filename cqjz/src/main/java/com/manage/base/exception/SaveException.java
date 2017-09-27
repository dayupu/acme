package com.manage.base.exception;

import com.manage.base.supplier.msgs.MessageErrors;

/**
 * Created by bert on 17-8-8.
 */
public class SaveException extends CoreException {

    public SaveException() {
        super(MessageErrors.SAVE_FAILED);
    }
}
