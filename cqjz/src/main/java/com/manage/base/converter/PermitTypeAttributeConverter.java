package com.manage.base.converter;

import com.manage.base.enums.PermitType;
import javax.persistence.AttributeConverter;

public class PermitTypeAttributeConverter implements AttributeConverter<PermitType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PermitType permitType) {
        return permitType.dbValue();
    }

    @Override
    public PermitType convertToEntityAttribute(Integer constant) {
        for (PermitType type : PermitType.values()) {
            if (constant.equals(type.dbValue())) {
                return type;
            }
        }
        return null;
    }

}
