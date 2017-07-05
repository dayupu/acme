package com.manage.cache.exception;

/**
 * Created by bert on 2017/7/2.
 */
public class TokenException extends RuntimeException {
    public TokenException() {
    }

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenException(Throwable cause) {
        super(cause);
    }
}
