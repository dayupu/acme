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

    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }
}
