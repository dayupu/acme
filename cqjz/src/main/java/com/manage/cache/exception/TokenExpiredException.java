package com.manage.cache.exception;


public class TokenExpiredException extends TokenException {

    public TokenExpiredException() {
        super("local is expired");
    }

}
