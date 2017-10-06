package com.manage.base.exception;

/**
 * Created by bert on 2017/10/4.
 */
public class ActTaskNotFoundException extends RuntimeException {

    public ActTaskNotFoundException() {
    }

    public ActTaskNotFoundException(String message) {
        super(message);
    }

    public ActTaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActTaskNotFoundException(Throwable cause) {
        super(cause);
    }
}

