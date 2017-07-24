package com.manage.news.core.admin.service.impl;

import com.google.common.collect.Lists;
import com.manage.news.core.admin.service.IMenuService;
import com.manage.news.dto.MenuDto;
import com.manage.news.jpa.kernel.entity.Menu;
import com.manage.news.jpa.kernel.repository.MenuRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService implements IMenuService {

    @Autowired
    private MenuRepo menuRepo;

    @Override
    @Transactional
    public List<MenuDto> menuListByRoleIds(List<Long> roleIds) {
        List<MenuDto> menuResults = Lists.newArrayList();
        if (roleIds.isEmpty()) {
            return menuResults;
        }

        List<Menu> menuList = menuRepo.queryMenuListByRoleIds(roleIds);
        MenuDto menuDto;
        for (Menu menu : menuList) {
            if (menu.getLevel() != 1) {
                continue;
            }
            menuDto = toMenuDto(menu);
            for (Menu subMenu : menuList) {
                if (menu.getId().equals(subMenu.getParentId())) {
                    menuDto.getSubMenus().add(toMenuDto(subMenu));
                }
            }
            menuResults.add(menuDto);
        }
        return menuResults;
    }

    private MenuDto toMenuDto(Menu menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setName(menu.getName());
        menuDto.setImage(menu.getImage());
        menuDto.setUrl(menu.getUrl());
        return menuDto;
    }

}
