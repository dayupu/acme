package com.manage.base.converter;

import com.manage.base.enums.Permit;
import javax.persistence.AttributeConverter;

public class PermitAttributeConverter implements AttributeConverter<Permit, String> {

    @Override
    public String convertToDatabaseColumn(Permit permit) {
        if (permit == null) {
            return null;
        }
        return permit.dbValue();
    }

    @Override
    public Permit convertToEntityAttribute(String dbValue) {
        if (dbValue == null) {
            return null;
        }
        for (Permit permit : Permit.values()) {
            if (dbValue.equals(permit.dbValue())) {
                return permit;
            }
        }
        return null;
    }

}
