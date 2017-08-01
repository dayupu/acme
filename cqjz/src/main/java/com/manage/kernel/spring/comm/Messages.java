package com.manage.kernel.spring.comm;

import com.manage.kernel.spring.message.MessageSupplier;
import java.util.Locale;

public class Messages {

    private static MessageSupplier messageSupplier;

    private Messages() {
    }

    private static MessageSupplier getMessageSupplier() {
        if (messageSupplier == null) {
            messageSupplier = SpringUtils.getBean(MessageSupplier.class);
        }
        return messageSupplier;
    }

    public static String get(String code, Object... params) {
        return getMessageSupplier().getMessage(code, params);
    }

    public static String get(String code, Locale locale, Object... params) {
        return getMessageSupplier().getMessage(code, params, locale);
    }

}
