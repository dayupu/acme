package com.manage.base.utils;


import com.manage.base.exception.ValidateException;

public class Validator {

    public static void notNull(Object obj) {
        if (obj == null) {
            throw new ValidateException("obj is null");
        }
    }

    public static void notEmpty(Object obj) {
        if (obj == null || "".equals(obj)) {
            throw new ValidateException("obj is empty");
        }
    }

    public static void notBlank(String str) {
        if (str == null || "".equals(str.trim())) {
            throw new ValidateException("obj is blank");
        }
    }
}
