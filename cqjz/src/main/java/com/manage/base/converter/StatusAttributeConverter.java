package com.manage.base.converter;

import com.manage.base.enums.Status;
import javax.persistence.AttributeConverter;

public class StatusAttributeConverter implements AttributeConverter<Status, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Status status) {
        if (status == null) {
            return null;
        }
        return status.dbValue();
    }

    @Override
    public Status convertToEntityAttribute(Integer dbValue) {
        if (dbValue == null) {
            return null;
        }
        for (Status status : Status.values()) {
            if (dbValue.equals(status.dbValue())) {
                return status;
            }
        }
        return null;
    }
}
