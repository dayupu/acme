package com.manage.base.extend.converter;

import com.manage.base.extend.enums.PermitType;
import javax.persistence.AttributeConverter;

public class PermitTypeAttributeConverter implements AttributeConverter<PermitType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PermitType permitType) {
        if (permitType == null) {
            return null;
        }
        return permitType.dbValue();
    }

    @Override
    public PermitType convertToEntityAttribute(Integer dbValue) {
        if (dbValue == null) {
            return null;
        }

        for (PermitType type : PermitType.values()) {
            if (dbValue.equals(type.dbValue())) {
                return type;
            }
        }
        return null;
    }

}
