package com.manage.kernel.core.model.parser;


import com.manage.kernel.core.model.dto.DepartDto;
import com.manage.kernel.jpa.entity.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartParser {

    public static DepartDto toDto(Department department) {
        return toDto(department, new DepartDto());
    }

    public static List<DepartDto> toDtoList(List<Department> departments) {
        List<DepartDto> departDtos = new ArrayList<>();
        for (Department department : departments) {
            departDtos.add(toDto(department, new DepartDto()));
        }
        return departDtos;
    }

    private static DepartDto toDto(Department depart, DepartDto departDto) {
        departDto.setName(depart.getName());
        departDto.setCode(depart.getCode());
        departDto.setFullCode(depart.getFullCode());
        departDto.setLeaf(depart.isLeaf());
        departDto.setLevel(depart.getLevel());
        if (depart.getParentCode() != null) {
            departDto.setParentCode(depart.getParentCode());
            departDto.setParentName(depart.getParent().getName());
        }
        return departDto;
    }

}
