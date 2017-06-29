package com.manage.news.utils;

import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base32;

public class CryptoUtils {

    private final static SecureRandom random = new SecureRandom();

    public static String newRandomId() {

        // 120 bits of randomness
        // a Type 4 UUID has 122
        // but multiples of 40 are nicer for BASE-32
        // and 160 bits seems overkill (and inconvenient to type)
        // this will make a 24 digit code

        byte[] randomBytes = new byte[15];
        random.nextBytes(randomBytes);
        return new Base32().encodeAsString(randomBytes);

    }
}
