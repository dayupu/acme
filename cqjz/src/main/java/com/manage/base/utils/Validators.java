package com.manage.base.utils;

import com.manage.base.exception.ValidateException;

public class Validators {

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new ValidateException(message);
        }
    }

    public static void notEmpty(Object obj, String message) {
        if (obj == null || "".equals(obj)) {
            throw new ValidateException(message);
        }
    }

    public static void notBlank(String str, String message) {
        if (str == null || "".equals(str.trim())) {
            throw new ValidateException(message);
        }
    }
}
