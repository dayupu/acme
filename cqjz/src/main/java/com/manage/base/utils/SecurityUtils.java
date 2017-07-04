package com.manage.base.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {

    private static final String ALGORITHM_MD5 = "MD5";

    public static String encryptMD5(String input) {
        try {
            MessageDigest mdInst = MessageDigest.getInstance(ALGORITHM_MD5);
            mdInst.update(input.getBytes());
            byte[] md = mdInst.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                String shaHex = Integer.toHexString(md[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
