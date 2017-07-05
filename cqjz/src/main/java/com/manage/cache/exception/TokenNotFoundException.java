package com.manage.cache.exception;


public class TokenNotFoundException extends TokenException {

    public TokenNotFoundException() {
        super("local not found");
    }

}
