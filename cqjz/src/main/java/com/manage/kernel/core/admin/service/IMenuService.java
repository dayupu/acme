package com.manage.kernel.core.admin.service;

import com.manage.kernel.dto.MenuNav;

import java.util.List;

public interface IMenuService {
    List<MenuNav> menuListByRoleIds(List<Long> roleIds);
}
