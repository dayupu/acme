package com.manage.base.exception;

import com.manage.base.supplier.msgs.MessageErrors;

/**
 * Created by bert on 17-8-8.
 */
public class MenuNotFoundException extends NotFoundException {

    public MenuNotFoundException() {
        super(MessageErrors.MENU_NOT_FOUND);
    }


}
