package com.manage.news.core.admin.service;

import com.manage.news.dto.MenuNav;

import java.util.List;

public interface IMenuService {
    List<MenuNav> menuListByRoleIds(List<Long> roleIds);
}
