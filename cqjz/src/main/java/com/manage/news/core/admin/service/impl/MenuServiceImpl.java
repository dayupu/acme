package com.manage.news.core.admin.service.impl;

import com.manage.news.core.admin.service.MenuService;
import com.manage.news.dto.MenuDto;
import com.manage.news.jpa.kernel.entity.Menu;
import com.manage.news.jpa.kernel.repository.MenuRepo;
import com.manage.news.jpa.kernel.repository.RoleRepo;
import java.util.ArrayList;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    @Transactional
    public List<MenuDto> menuListByRoleIds(List<Long> roleIds) {

        List<Menu> menuList = roleRepo.queryMenuListByRoleIds(roleIds);
        menuList.sort(new menuComparator());

        for (Menu menu : menuList) {
            System.out.println(menu.getLevel() + ":" + menu.getSequence());
        }

        return null;
    }

    private List<MenuDto> subMenus(Long menuId, int level, List<Menu> menuList) {
        List<MenuDto> menuDtos = new ArrayList<MenuDto>();
        for (Menu menu : menuList) {
            if (menu.getLevel() != level) {
                continue;
            }

            if (menuId.equals(menu.getParentId())) {
                menuDtos.add(convertToMenuDto(menu));
            }
        }

        return menuDtos;
    }

    private MenuDto convertToMenuDto(Menu menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setName(menu.getName());
        menuDto.setImage(menu.getImage());
        menuDto.setUrl(menu.getUrl());

        return null;
    }

    private class menuComparator implements Comparator<Menu> {

        @Override
        public int compare(Menu first, Menu second) {

            if (first.getLevel() > second.getLevel()) {
                return 1;
            }

            if (first.getLevel() < second.getLevel()) {
                return -1;
            }

            return compareSequence(first, second);
        }

        private int compareSequence(Menu first, Menu second) {
            if (first.getSequence() > second.getSequence()) {
                return 1;
            } else if (first.getSequence() < second.getSequence()) {
                return -1;
            }
            return 0;
        }
    }

}
