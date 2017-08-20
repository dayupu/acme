package com.manage.base.exception;

import com.manage.kernel.spring.comm.Messages;

/**
 * Created by bert on 2017/8/20.
 */
public class ValidateParamException extends ValidateException{

    public ValidateParamException() {
        super(Messages.get("message.validate.no.pass"));
    }
}
