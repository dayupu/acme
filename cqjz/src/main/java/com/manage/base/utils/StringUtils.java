package com.manage.base.utils;


public class StringUtils extends org.springframework.util.StringUtils {

    public static boolean isEmptyAll(Object... objects) {
        for (Object object : objects) {
            if (!isEmpty(object)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmptyAny(Object... objects) {
        for (Object object : objects) {
            if (isEmpty(object)) {
                return true;
            }
        }
        return false;
    }
}
