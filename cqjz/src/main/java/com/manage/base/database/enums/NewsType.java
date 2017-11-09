package com.manage.base.database.enums;

import com.manage.base.database.model.DBEnum;
import com.manage.base.database.model.Localizable;
import com.manage.kernel.spring.comm.Messages;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/9/3.
 */
public enum NewsType implements DBEnum, Localizable {

    //专题-首页新闻
    TOPIC(0, "resource.constant.news.topic.home", false),
    //图片新闻
    TPXW(10, "resource.constant.news.type.tpxw", true),
    //警情快讯
    JQKX(11, "resource.constant.news.type.jqkx", false),
    //队伍建设
    DWJS(12, "resource.constant.news.type.dwjs", false),
    //部门动态
    BMDT(13, "resource.constant.news.type.bmdt", false),
    //学习园地
    XXYD(14, "resource.constant.news.type.xxyd", false),
    //网海拾贝
    WHSB(15, "resource.constant.news.type.whsb", false),
    //科技瞭望
    KJLW(16, "resource.constant.news.type.kjlw", false);

    private int constant;
    private String messageKey;
    private boolean hasImage;
    private static List<NewsType> newsTypes = new ArrayList<NewsType>() {{
        add(TPXW);
        add(JQKX);
        add(DWJS);
        add(BMDT);
        add(XXYD);
        add(WHSB);
        add(KJLW);
    }};

    NewsType(int constant, String messageKey, boolean hasImage) {
        this.constant = constant;
        this.messageKey = messageKey;
        this.hasImage = hasImage;
    }

    public static NewsType fromTypeName(String typeName) {
        if (typeName != null) {
            for (NewsType type : NewsType.getTypeList()) {
                if (type.name().equalsIgnoreCase(typeName)) {
                    return type;
                }
            }
        }
        return null;
    }

    public static String getTypeName(Integer constant) {
        if (constant != null) {
            for (NewsType type : NewsType.getTypeList()) {
                if (type.getConstant() == constant.intValue()) {
                    return type.message();
                }
            }
        }
        return null;
    }

    public static List<NewsType> getTypeList() {
        return newsTypes;
    }

    @Override
    public Integer getConstant() {
        return constant;
    }

    @Override
    public String messageKey() {
        return messageKey;
    }

    private String message() {
        return Messages.get(messageKey);
    }

    public boolean hasImage() {
        return hasImage;
    }

    public boolean requireImage() {
        return this == TPXW;
    }

}
