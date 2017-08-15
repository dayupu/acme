package com.manage.base.utils;

import com.manage.base.extend.enums.Gender;

/**
 * Created by bert on 2017/8/13.
 */
public class EnumUtils {

    public static Gender toGender(Integer value) {
        if (value != null) {
            for (Gender gender : Gender.values()) {
                if (value.equals(gender.getConstant())) {
                    return gender;
                }
            }
        }
        return null;
    }

}
