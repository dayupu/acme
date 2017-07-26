package com.manage.news.core.admin.service.impl;

import com.google.common.collect.Lists;
import com.manage.news.core.admin.service.IMenuService;
import com.manage.news.dto.MenuBar;
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
    public List<MenuBar> menuListByRoleIds(List<Long> roleIds) {
        List<MenuBar> menuResults = Lists.newArrayList();
        if (roleIds.isEmpty()) {
            return menuResults;
        }

        List<Menu> menuList = menuRepo.queryMenuListByRoleIds(roleIds);
        MenuBar menuBar;
        for (Menu menu : menuList) {
            if (menu.getLevel() != 1) {
                continue;
            }
            menuBar = toMenuDto(menu);
            for (Menu subMenu : menuList) {
                if (menu.getId().equals(subMenu.getParentId())) {
                    menuBar.getSubMenus().add(toMenuDto(subMenu));
                }
            }
            menuResults.add(menuBar);
        }
        return menuResults;
    }

    private MenuBar toMenuDto(Menu menu) {
        MenuBar menuBar = new MenuBar();
        menuBar.setId(menu.getId());
        menuBar.setName(menu.getName());
        menuBar.setImage(menu.getImage());
        menuBar.setUrl(menu.getUrl());
        return menuBar;
    }

}
