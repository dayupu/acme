package com.manage.kernel.core.admin.service.impl;

import com.google.common.collect.Lists;
import com.manage.base.supplier.TreeNode;
import com.manage.kernel.core.admin.dto.MenuDto;
import com.manage.kernel.core.admin.dto.MenuNav;
import com.manage.kernel.core.admin.service.IMenuService;
import com.manage.kernel.jpa.news.entity.Menu;
import com.manage.kernel.jpa.news.repository.MenuRepo;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.criteria.Predicate;

@Service
public class MenuService implements IMenuService {

    @Autowired
    private MenuRepo menuRepo;

    @Override
    @Transactional
    public MenuDto updateMenu(Long id, MenuDto menuDto) {

        Menu menu = menuRepo.findOne(id);
        menu.setName(menuDto.getName());
        menu.setUrl(menuDto.getUrl());
        List<Menu> brotherMenus;

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
            for (Menu brother : brotherMenus) {

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

        Menu menu = menuRepo.findOne(id);
        MenuDto menuDto = null;
        if (menu != null) {
            menuDto = new MenuDto();
            menuDto.setId(menu.getId());
            menuDto.setName(menu.getName());
            menuDto.setUrl(menu.getUrl());
            menuDto.setLevel(menu.getLevel());
            menuDto.setSequence(menu.getSequence());
            menuDto.setParentId(menu.getParentId());
            if (menu.getParent() != null) {
                menuDto.setParentName(menu.getParent().getName());
            }
        }
        return menuDto;
    }

    @Override
    public List<TreeNode> menuTree() {

        Iterable<Menu> menuIterables = menuRepo.queryMenuAll();
        List<TreeNode> treeNodes = new ArrayList<>();
        TreeNode treeNode;
        for (Menu menu : menuIterables) {
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

        List<Menu> menuList = menuRepo.queryMenuListByRoleIds(roleIds);
        MenuNav menuNav;
        for (Menu menu : menuList) {
            if (menu.getLevel() != 1) {
                continue;
            }
            menuNav = MenuNav.forMenu(menu);
            for (Menu subMenu : menuList) {
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
        List<Menu> menus = menuRepo.queryMenuListByUrl(url);
        if (menus.isEmpty()) {
            return new ArrayList<>();
        }

        List<MenuNav> menuNavs = new ArrayList<>();
        Menu menu = menus.get(0);
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
}
