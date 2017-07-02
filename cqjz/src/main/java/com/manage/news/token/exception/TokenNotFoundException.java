package com.manage.news.token.exception;


public class TokenNotFoundException extends TokenException {

    public TokenNotFoundException() {
        super("token not found");
    }

}
