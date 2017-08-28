package com.manage.base.utils;


import java.text.DecimalFormat;

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


    public static String fileSize(Long size) {
        if (size == null) {
            return null;
        }
        double result = (double) size / 1024;
        String unit = "KB";
        if (result > 1024) {
            result = result / 1024;
            unit = "MB";
            if (result > 1024) {
                result = result / 1024;
                unit = "GB";
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(result) + unit;
    }
}
