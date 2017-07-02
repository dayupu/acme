package com.manage.news.token.exception;


public class TokenExpiredException extends TokenException {

    public TokenExpiredException() {
        super("token is expired");
    }

}
