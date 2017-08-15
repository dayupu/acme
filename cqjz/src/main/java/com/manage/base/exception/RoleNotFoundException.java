package com.manage.base.exception;

import com.manage.base.supplier.msgs.MessageErrors;

/**
 * Created by bert on 17-8-8.
 */
public class RoleNotFoundException extends NotFoundException {

    public RoleNotFoundException() {
        super(MessageErrors.ROLE_NOT_FOUND);
    }

}
