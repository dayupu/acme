package com.manage.base.exception;

import com.manage.base.supplier.msgs.MessageErrors;

/**
 * Created by bert on 17-8-8.
 */
public class SuperstarNotFoundException extends NotFoundException {

    public SuperstarNotFoundException() {
        super(MessageErrors.SUPERSTAR_NOT_FOUND);
    }


}
