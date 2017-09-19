package com.manage.base.utils;

import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base32;

/**
 * Created by bert on 2017/8/20.
 */
public class CoreUtil {

    private static final int DEPART_LENGTH = 12;
    private final static SecureRandom random = new SecureRandom();

    public static int departLevel(String code) {
        return departCodeSimple(code).length() / 2;
    }

    /**
     * 12位管辖机构代码
     * @param code
     * @return
     */
    public static String departCodeFull(String code) {
        if (code.length() >= DEPART_LENGTH) {
            return code;
        }
        while (code.length() < DEPART_LENGTH) {
            code += "0";
        }
        return code;
    }

    /**
     * 管辖机构简码
     * @param code
     * @return
     */
    public static String departCodeSimple(String code) {
        String simpleCode = code;
        while (simpleCode.endsWith("00")) {
            simpleCode = simpleCode.substring(0, simpleCode.length() - 2);
        }
        return simpleCode;
    }

    /**
     * 生成随机24位唯一ID
     * @return
     */
    public static String nextRandomID() {
        byte[] randomBytes = new byte[15];
        random.nextBytes(randomBytes);
        return new Base32().encodeAsString(randomBytes);
    }
}