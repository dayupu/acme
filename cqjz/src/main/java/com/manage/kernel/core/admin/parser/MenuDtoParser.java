package com.manage.kernel.core.admin.parser;


import com.manage.kernel.core.admin.dto.MenuDto;
import com.manage.kernel.jpa.news.entity.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuDtoParser {

    public static MenuDto toMenuDto(Menu menu) {
        return toMenuDto(menu, new MenuDto());
    }

    public static List<MenuDto> toMenuDtoList(List<Menu> menus) {
        List<MenuDto> menuDtos = new ArrayList<>();
        for (Menu menu : menus) {
            menuDtos.add(toMenuDto(menu, new MenuDto()));
        }
        return menuDtos;
    }

    private static MenuDto toMenuDto(Menu menu, MenuDto menuDto) {
        menuDto.setId(menu.getId());
        menuDto.setImage(menu.getImage());
        menuDto.setLevel(menu.getLevel());
        menuDto.setName(menu.getName());
        menuDto.setParentId(menu.getParentId());
        return menuDto;
    }


}
