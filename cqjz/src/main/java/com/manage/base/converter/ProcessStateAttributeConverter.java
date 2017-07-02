package com.manage.base.converter;

import com.manage.base.enums.ProcessState;

import javax.persistence.AttributeConverter;

public class ProcessStateAttributeConverter implements AttributeConverter<ProcessState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProcessState processState) {
        return processState.dbValue();
    }

    @Override
    public ProcessState convertToEntityAttribute(Integer integer) {
        for (ProcessState state : ProcessState.values()) {
            if (integer.equals(state.getConstant())) {
                return state;
            }
        }
        return null;
    }

}
