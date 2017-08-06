package com.manage.kernel.core.admin.service;

import com.manage.base.atomic.TreeNode;
import com.manage.kernel.core.admin.dto.MenuDto;
import com.manage.kernel.core.admin.dto.MenuNav;

import java.util.List;

public interface IMenuService {
    List<MenuNav> menuListByRoleIds(List<Long> roleIds);

    List<TreeNode> menuTree();

    MenuDto getMenu(Long id);

    MenuDto updateMenu(Long id, MenuDto menuDto);

    List<MenuNav> menuLocation(String url);
}
