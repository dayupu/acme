package com.manage.base.exception;

import com.manage.base.supplier.msgs.MessageErrors;

/**
 * Created by bert on 17-8-8.
 */
public class StyleNotFoundException extends NotFoundException {

    public StyleNotFoundException() {
        super(MessageErrors.STYLE_NOT_FOUND);
    }

}
