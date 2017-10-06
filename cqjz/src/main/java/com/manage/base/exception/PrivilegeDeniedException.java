package com.manage.base.exception;

import com.manage.base.supplier.msgs.MessageErrors;

/**
 * Created by bert on 17-8-8.
 */
public class PrivilegeDeniedException extends CoreException {

    public PrivilegeDeniedException() {
        super(MessageErrors.PRIVILEGE_DENIED);
    }

}
