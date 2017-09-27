package com.manage.kernel.core.admin.apply.parser;


import com.manage.kernel.core.admin.apply.dto.DepartDto;
import com.manage.kernel.core.admin.apply.dto.MenuDto;
import com.manage.kernel.jpa.news.entity.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartParser {

    public static DepartDto toDepartDto(Department department) {
        return toDepartDto(department, new DepartDto());
    }

    public static List<DepartDto> toDepartDtoList(List<Department> departments) {
        List<DepartDto> departDtos = new ArrayList<>();
        for (Department department : departments) {
            departDtos.add(toDepartDto(department, new DepartDto()));
        }
        return departDtos;
    }

    private static DepartDto toDepartDto(Department depart, DepartDto departDto) {
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
