package com.manage.base.exception;

import com.manage.base.supplier.msgs.MessageErrors;

/**
 * Created by bert on 17-8-8.
 */
public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super(MessageErrors.USER_NOT_FOUND);
    }


}
