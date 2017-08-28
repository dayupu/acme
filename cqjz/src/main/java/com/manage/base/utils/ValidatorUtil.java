package com.manage.base.utils;

import com.manage.base.exception.ValidateException;
import com.manage.base.exception.ValidateParamException;
import com.manage.kernel.spring.comm.Messages;

import java.util.Collection;
import java.util.Collections;

public class ValidatorUtil {

    public static void notNull(Object obj) {
        if (obj == null) {
            throw new ValidateParamException();
        }
    }

    public static void notEmpty(String obj) {
        if (obj == null || "".equals(obj)) {
            throw new ValidateParamException();
        }
    }

    public static void notEmpty(Collection obj) {
        if (obj == null || obj.isEmpty()) {
            throw new ValidateParamException();
        }
    }

    public static void notBlank(String str) {
        if (str == null || "".equals(str.trim())) {
            throw new ValidateParamException();
        }
    }
}
