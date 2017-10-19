package com.manage.base.act.enums;

import com.manage.base.database.enums.NewsType;
import com.manage.base.database.model.Localizable;
import com.manage.kernel.spring.comm.Messages;

/**
 * Created by bert on 2017/10/4.
 */
public enum ActSource implements Localizable {

    NEWS("NEWS", "resource.approve.type.news");

    private String key;
    private String messageKey;

    ActSource(String key, String messageKey) {
        this.key = key;
        this.messageKey = messageKey;
    }

    public boolean isNews() {
        return this == NEWS;
    }

    public String getKey() {
        return key;
    }

    public String processTypeGen(Enum enumType) {
        if (enumType == null) {
            return null;
        }
        if (this == NEWS) {
            NewsType type = (NewsType) enumType;
            return getKey() + "_" + type.getConstant();
        }
        return null;
    }

    public static String processTypeName(String processType) {
        if (processType == null) {
            return null;
        }

        String[] types = processType.split("_");
        ActSource source = fromKey(types[0]);
        if (source.isNews()) {
            String newsTypeName = NewsType.getTypeName(Integer.valueOf(types[1]));
            return source.message() + " - " + newsTypeName;
        }
        return null;
    }

    public static ActSource fromKey(String key) {
        for (ActSource source : ActSource.values()) {
            if (source.getKey().equals(key)) {
                return source;
            }
        }
        return null;
    }

    public String message(){
        return Messages.get(messageKey());
    }

    @Override
    public String messageKey() {
        return messageKey;
    }
}
