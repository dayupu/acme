package com.manage.base.utils;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base32;
import org.joda.time.LocalDateTime;

import static org.apache.coyote.http11.Constants.a;

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
     *
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
     *
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
     *
     * @return
     */
    public static String nextRandomID() {
        byte[] randomBytes = new byte[15];
        random.nextBytes(randomBytes);
        return new Base32().encodeAsString(randomBytes);
    }

    public static <T> boolean equals(T obj1, T obj2) {
        if (obj1 == obj2) {
            return true;
        }
        return obj1.equals(obj2);
    }

    public static List removeDuplicate(List list) {
        List listTemp = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!listTemp.contains(list.get(i))) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }

    public static <T> boolean notEquals(T obj1, T obj2) {
        return !equals(obj1, obj2);
    }


    public static String toDatetimeStr(LocalDateTime dateTime){
        if(dateTime == null){
            return null;
        }
        return dateTime.toString("yyyy-MM-dd HH:mm:ss");
    }

    public static LocalDateTime fromDate(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.fromDateFields(date);
    }

    public static String format(String text, Object... params) {
        return MessageFormat.format(text, params);
    }
}
