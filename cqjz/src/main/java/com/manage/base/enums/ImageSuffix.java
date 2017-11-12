package com.manage.base.enums;

import com.manage.base.utils.StringHandler;

/**
 * Created by bert on 2017/10/21.
 */
public enum ImageSuffix {

    JPEG("jpg", "jpeg");

    private String originType;
    private String type;

    ImageSuffix(String originType, String type) {
        this.originType = originType;
        this.type = type;
    }

    public static String imageSuffixFilter(String imageType) {
        if (StringHandler.isBlank(imageType)) {
            return null;
        }
        for (ImageSuffix suffix : ImageSuffix.values()) {
            if (suffix.getOriginType().equals(imageType)) {
                return suffix.getType();
            }
        }
        return imageType;
    }

    public String getOriginType() {
        return originType;
    }

    public String getType() {
        return type;
    }
}
