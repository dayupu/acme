package com.manage.base.utils;

/**
 * Created by bert on 2017/8/20.
 */
public class CoreUtils {

    private static final int DEPART_LENGTH = 12;

    public static int departLevel(String code) {
        return departCodeSimple(code).length() / 2;
    }

    public static String departCodeFull(String code) {
        if (code.length() >= DEPART_LENGTH) {
            return code;
        }
        while (code.length() < DEPART_LENGTH) {
            code += "0";
        }
        return code;
    }

    public static String departCodeSimple(String code) {
        String simpleCode = code;
        while (simpleCode.endsWith("00")) {
            simpleCode = simpleCode.substring(0, simpleCode.length() - 2);
        }
        return simpleCode;
    }

}
