package com.manage.news.spring.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

public class AuthPasswordEncoder implements PasswordEncoder {

    private static final String SECRET_KEY = "cqjz2017";

    private static final PasswordEncoder ENCODER = new StandardPasswordEncoder(SECRET_KEY);

    @Override
    public String encode(CharSequence charSequence) {
        return ENCODER.encode(charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return ENCODER.matches(charSequence, s);
    }

    public static void main(String[] args) {
        AuthPasswordEncoder authPasswordEncoder = new AuthPasswordEncoder();
        System.out.println(authPasswordEncoder.encode("a"));
    }
}
