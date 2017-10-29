package com.manage.kernel.core.admin.service.system;

import com.manage.base.supplier.page.TreeNode;
import com.manage.kernel.core.model.dto.MenuDto;
import com.manage.kernel.core.model.dto.MenuNav;

import java.util.List;

public interface IMenuService {
    List<MenuNav> menuListByRoleIds(List<Long> roleIds);

    List<TreeNode> menuTree();

    MenuDto getMenu(Long id);

    MenuDto updateMenu(Long id, MenuDto menuDto);

    void deleteMenu(Long id);

    void addMenu(MenuDto menuDto);
}
