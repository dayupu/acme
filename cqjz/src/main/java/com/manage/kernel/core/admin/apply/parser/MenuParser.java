package com.manage.kernel.core.admin.apply.parser;


import com.manage.kernel.core.admin.apply.dto.MenuDto;
import com.manage.kernel.jpa.entity.AdMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuParser {

    public static MenuDto toMenuDto(AdMenu menu) {
        return toMenuDto(menu, new MenuDto());
    }

    public static List<MenuDto> toMenuDtoList(List<AdMenu> menus) {
        List<MenuDto> menuDtos = new ArrayList<>();
        for (AdMenu menu : menus) {
            menuDtos.add(toMenuDto(menu, new MenuDto()));
        }
        return menuDtos;
    }

    private static MenuDto toMenuDto(AdMenu menu, MenuDto menuDto) {
        menuDto.setId(menu.getId());
        menuDto.setImage(menu.getImage());
        menuDto.setLevel(menu.getLevel());
        menuDto.setName(menu.getName());
        menuDto.setParentId(menu.getParentId());
        menuDto.setUrl(menu.getUrl());
        return menuDto;
    }

}
