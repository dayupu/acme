package com.manage.kernel.core.model.parser;

import com.manage.kernel.core.model.dto.SuperstarDto;
import com.manage.kernel.jpa.entity.JzSuperStar;

import java.util.ArrayList;
import java.util.List;

public class SuperstarParser {

    public static SuperstarDto toDto(JzSuperStar superStar) {
        return toDto(superStar, new SuperstarDto());
    }

    public static List<SuperstarDto> toDtoList(List<JzSuperStar> superStars) {
        List<SuperstarDto> results = new ArrayList<>();
        for (JzSuperStar superStar : superStars) {
            results.add(toDto(superStar, new SuperstarDto()));
        }
        return results;
    }

    private static SuperstarDto toDto(JzSuperStar superStar, SuperstarDto superstarDto) {
        superstarDto.setId(superStar.getId());
        superstarDto.setYear(superStar.getYear());
        superstarDto.setMonth(superStar.getMonth());
        superstarDto.setHonor(superStar.getHonor());
        superstarDto.setName(superStar.getName());
        superstarDto.setStory(superStar.getStory());
        return superstarDto;
    }
}
