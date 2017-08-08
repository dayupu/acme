package com.manage.base.exception;

import com.manage.base.supplier.msgs.MessageErrors;

/**
 * Created by bert on 17-8-8.
 */
public class DeleteException extends CoreException{

    public DeleteException() {
        super(MessageErrors.DELETE_FAILED);
    }

}
