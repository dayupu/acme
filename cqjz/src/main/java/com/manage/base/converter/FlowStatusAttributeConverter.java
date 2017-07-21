package com.manage.base.converter;

import com.manage.base.enums.FlowStatus;

import javax.persistence.AttributeConverter;

public class FlowStatusAttributeConverter implements AttributeConverter<FlowStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(FlowStatus processState) {
        return processState.dbValue();
    }

    @Override
    public FlowStatus convertToEntityAttribute(Integer integer) {
        for (FlowStatus state : FlowStatus.values()) {
            if (integer.equals(state.getConstant())) {
                return state;
            }
        }
        return null;
    }

}
