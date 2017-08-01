package com.manage.kernel.core.admin.service.impl;

import com.google.common.collect.Lists;
import com.manage.kernel.core.admin.dto.MenuNav;
import com.manage.kernel.core.admin.service.IMenuService;
import com.manage.kernel.jpa.news.entity.Menu;
import com.manage.kernel.jpa.news.repository.MenuRepo;

import java.util.ArrayList;
import com.manage.kernel.spring.entry.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;

@Service
public class MenuService implements IMenuService {

    @Autowired
    private MenuRepo menuRepo;

    @Override
    public Page<Menu> menuList(PageQuery pageQuery) {

        Page<Menu> menuPage = menuRepo.findAll((Specification<Menu>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        }, pageQuery.buildPageRequest(false));
        return menuPage;
    }

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
