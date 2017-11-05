package com.manage.kernel.core.model.parser;

import com.manage.kernel.core.model.dto.ContactsDto;
import com.manage.kernel.core.model.dto.StyleDto;
import com.manage.kernel.jpa.entity.JzContacts;
import com.manage.kernel.jpa.entity.JzStyle;
import com.manage.kernel.jpa.entity.JzStyleLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/8/26.
 */
public class StyleParser {

    public static StyleDto toDto(JzStyle entity) {
        return toDto(entity, new StyleDto());
    }

    public static List<StyleDto> toDtoList(List<JzStyle> entitys) {
        List<StyleDto> dtos = new ArrayList<>();
        for (JzStyle entity : entitys) {
            dtos.add(toDto(entity, new StyleDto()));
        }
        return dtos;
    }

    private static StyleDto toDto(JzStyle entity, StyleDto dto) {
        dto.setTitle(entity.getTitle());
        dto.setNumber(entity.getNumber());
        for (JzStyleLine styleLine : entity.getStyleLines()) {
            StyleDto.StyleLine styleLineDto = new StyleDto.StyleLine();
            styleLineDto.setImageId(styleLine.getImageId());
            styleLineDto.setDescription(styleLine.getDescription());
            dto.getStyleLines().add(styleLineDto);
        }
        return dto;
    }
}
