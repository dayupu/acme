package com.manage.base.exception;

import com.manage.base.supplier.msgs.MessageErrors;

/**
 * Created by bert on 17-8-8.
 */
public class OrganNotFoundException extends NotFoundException {

    public OrganNotFoundException() {
        super(MessageErrors.DEPART_NOT_FOUND);
    }


}
