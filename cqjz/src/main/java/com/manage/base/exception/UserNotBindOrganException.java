package com.manage.base.exception;

import com.manage.base.supplier.msgs.MessageErrors;

/**
 * Created by bert on 17-8-8.
 */
public class UserNotBindOrganException extends NotFoundException {

    public UserNotBindOrganException() {
        super(MessageErrors.USER_NOT_BIND_ORGAN);
    }


}
