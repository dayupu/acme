package com.manage.kernel.core.admin.service.impl;

import com.google.common.collect.Lists;
import com.manage.kernel.core.admin.service.IMenuService;
import com.manage.kernel.dto.MenuNav;
import com.manage.kernel.jpa.news.entity.Menu;
import com.manage.kernel.jpa.news.repository.MenuRepo;
import java.util.ArrayList;
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
    public List<MenuNav> menuListByRoleIds(List<Long> roleIds) {
        List<MenuNav> menuResults = Lists.newArrayList();
        if (roleIds.isEmpty()) {
            return menuResults;
        }

        List<Menu> menuList = menuRepo.queryMenuListByRoleIds(roleIds);
        MenuNav menuNav;
        MenuNav subMenuNav;
        List<MenuNav.Location> locations;
        List<MenuNav.Location> subLocations;
        for (Menu menu : menuList) {
            if (menu.getLevel() != 1) {
                continue;
            }
            menuNav = MenuNav.forMenu(menu);
            locations = new ArrayList<>();
            locations.add(MenuNav.Location.forMenu(menu));
            menuNav.setLocations(locations);
            for (Menu subMenu : menuList) {
                if (menu.getId().equals(subMenu.getParentId())) {
                    subMenuNav = MenuNav.forMenu(subMenu);
                    subLocations = new ArrayList<>();
                    subLocations.addAll(locations);
                    subLocations.add(MenuNav.Location.forMenu(subMenu));
                    subMenuNav.setLocations(subLocations);
                    menuNav.getSubMenus().add(subMenuNav);
                }
            }
            menuResults.add(menuNav);
        }
        return menuResults;
    }

}
