package com.manage.kernel.core.model.parser;


import com.manage.kernel.core.model.dto.OrganDto;
import com.manage.kernel.jpa.entity.AdOrganization;

import java.util.ArrayList;
import java.util.List;

public class OrganParser {

    public static OrganDto toDto(AdOrganization organ) {
        return toDto(organ, new OrganDto());
    }

    public static List<OrganDto> toDtoList(List<AdOrganization> organs) {
        List<OrganDto> OrganDtos = new ArrayList<>();
        for (AdOrganization organ : organs) {
            OrganDtos.add(toDto(organ, new OrganDto()));
        }
        return OrganDtos;
    }

    private static OrganDto toDto(AdOrganization organ, OrganDto organDto) {
        organDto.setId(organ.getId());
        organDto.setName(organ.getName());
        organDto.setLevel(organ.getLevel());
        organDto.setSequence(organ.getSequence());
        organDto.setDescription(organ.getDescription());
        if (organ.getParentId() != null) {
            organDto.setParentId(organ.getParentId());
            organDto.setParentName(organ.getParent().getName());
        }
        return organDto;
    }

}
