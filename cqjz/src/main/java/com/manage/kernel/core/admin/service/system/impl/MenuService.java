package com.manage.kernel.core.admin.service.system.impl;

import com.google.common.collect.Lists;
import com.manage.base.exception.DeleteException;
import com.manage.base.exception.MenuNotFoundException;
import com.manage.base.supplier.page.TreeNode;
import com.manage.kernel.core.admin.apply.dto.MenuDto;
import com.manage.kernel.core.admin.apply.dto.MenuNav;
import com.manage.kernel.core.admin.service.system.IMenuService;
import com.manage.kernel.jpa.entity.AdMenu;
import com.manage.kernel.jpa.repository.AdMenuRepo;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;

import org.springframework.util.CollectionUtils;

@Service
public class MenuService implements IMenuService {

    private static final Logger LOGGER = LogManager.getLogger(MenuService.class);

    @Autowired
    private AdMenuRepo menuRepo;

    @Override
    @Transactional
    public void addMenu(MenuDto menuDto) {

        AdMenu menu = new AdMenu();
        menu.setName(menuDto.getName());
        menu.setUrl(menuDto.getUrl());
        if (menuDto.getParentId() == null) {
            List<AdMenu> menus = menuRepo.queryListByLevel(1);
            menu.setLevel(1);
            menu.setSequence(menus.size() + 1);
        } else {
            AdMenu parent = menuRepo.findOne(menuDto.getParentId());
            if (parent == null) {
                LOGGER.warn("Not found the order {}", menuDto.getParentId());
                throw new MenuNotFoundException();
            }
            menu.setParent(parent);
            menu.setLevel(parent.getLevel() + 1);
            menu.setSequence(parent.getChildrens().size() + 1);
        }
        menuRepo.save(menu);
    }

    @Override
    @Transactional
    public MenuDto updateMenu(Long id, MenuDto menuDto) {

        AdMenu menu = menuRepo.findOne(id);
        if (menu == null) {
            LOGGER.warn("Not found the menu {}", id);
            throw new MenuNotFoundException();
        }

        menu.setName(menuDto.getName());
        menu.setUrl(menuDto.getUrl());
        List<AdMenu> brotherMenus;

        int seqNew = menuDto.getSequence();
        int seqOld = menu.getSequence();
        if (seqNew != seqOld) {
            brotherMenus = menuRepo.findAll((root, cq, cb) -> {
                List<Predicate> list = new ArrayList<>();
                if (menu.getParentId() == null) {
                    list.add(cb.isNull(root.get("parentId")));
                } else {
                    list.add(cb.equal(root.get("parentId").as(Long.class), menu.getParentId()));
                }
                return cb.and(list.toArray(new Predicate[0]));
            }, new Sort(Sort.Direction.ASC, "sequence"));

            if (seqNew > brotherMenus.size()) {
                seqNew = brotherMenus.size();
            }
            menu.setSequence(seqNew);
            int start = seqNew > seqOld ? seqOld : seqNew;
            int end = seqNew > seqOld ? seqNew : seqOld;
            int sequence = start;
            boolean isDown = true;
            for (AdMenu brother : brotherMenus) {
                if (seqNew < seqOld && isDown) {
                    sequence++;
                    isDown = false;
                }
                if (brother.getId().equals(menu.getId())) {
                    continue;
                }
                if (brother.getSequence() >= start && brother.getSequence() <= end) {
                    brother.setSequence(sequence++);
                    menuRepo.save(brother);
                    continue;
                }
            }
        }

        menuRepo.save(menu);
        return menuDto;
    }

    @Override
    @Transactional
    public MenuDto getMenu(Long id) {

        AdMenu menu = menuRepo.findOne(id);
        if (menu == null) {
            return null;
        }

        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setName(menu.getName());
        menuDto.setUrl(menu.getUrl());
        menuDto.setLevel(menu.getLevel());
        menuDto.setSequence(menu.getSequence());
        menuDto.setParentId(menu.getParentId());

        if (!CollectionUtils.isEmpty(menu.getChildrens())) {
            menuDto.setHasChildren(true);
        }
        if (menu.getParent() != null) {
            menuDto.setParentName(menu.getParent().getName());
        }

        return menuDto;
    }

    @Override
    @Transactional
    public List<TreeNode> menuTree() {

        Iterable<AdMenu> menuIterables = menuRepo.queryListAll();
        List<TreeNode> treeNodes = new ArrayList<>();
        TreeNode treeNode;
        for (AdMenu menu : menuIterables) {
            treeNode = new TreeNode();
            treeNode.setId(menu.getId());
            treeNode.setPid(menu.getParentId());
            treeNode.setName(menu.getName());
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    @Override
    @Transactional
    public List<MenuNav> menuListByRoleIds(List<Long> roleIds) {
        List<MenuNav> menuResults = Lists.newArrayList();
        if (roleIds.isEmpty()) {
            return menuResults;
        }

        List<AdMenu> menuList = menuRepo.queryMenuListByRoleIds(roleIds);
        MenuNav menuNav;
        for (AdMenu menu : menuList) {
            if (menu.getLevel() != 1) {
                continue;
            }
            menuNav = MenuNav.forMenu(menu);
            for (AdMenu subMenu : menuList) {
                if (menu.getId().equals(subMenu.getParentId())) {
                    menuNav.getSubMenus().add(MenuNav.forMenu(subMenu));
                }
            }
            menuResults.add(menuNav);
        }
        return menuResults;
    }

    @Override
    @Transactional
    public List<MenuNav> menuLocation(String url) {
        List<AdMenu> menus = menuRepo.queryListByUrl(url);
        if (menus.isEmpty()) {
            return new ArrayList<>();
        }

        List<MenuNav> menuNavs = new ArrayList<>();
        AdMenu menu = menus.get(0);
        menuNavs.add(MenuNav.forMenu(menu));
        while (menu.getParentId() != null) {
            menu = menu.getParent();
            menuNavs.add(MenuNav.forMenu(menu));
        }

        Collections.sort(menuNavs, new Comparator<MenuNav>() {
            @Override
            public int compare(MenuNav o1, MenuNav o2) {
                if (o1.getLevel() > o2.getLevel()) {
                    return 1;
                }
                return -1;
            }
        });

        return menuNavs;
    }

    @Override
    @Transactional
    public void deleteMenu(Long id) {
        AdMenu menu = menuRepo.findOne(id);
        if (menu == null) {
            LOGGER.warn("Not found the menu {}", id);
            throw new MenuNotFoundException();
        }

        if (!CollectionUtils.isEmpty(menu.getChildrens())) {
            LOGGER.warn("The menu {} has childrens, can't delete.", menu.getId());
            throw new DeleteException();
        }

        int sequence = menu.getSequence();
        AdMenu pMenu = menu.getParent();
        if (pMenu != null) {
            int seqStart = sequence;
            for (AdMenu subMenu : pMenu.getChildrens()) {
                if (subMenu.getSequence() < sequence || id.equals(subMenu.getId())) {
                    continue;
                }

                subMenu.setSequence(seqStart++);
                menuRepo.save(subMenu);
            }
        }

        menu.setRoles(new ArrayList<>());
        menuRepo.delete(menu);
    }
}
