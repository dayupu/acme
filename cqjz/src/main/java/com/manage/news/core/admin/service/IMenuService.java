package com.manage.news.core.admin.service;

import com.manage.news.dto.MenuBar;

import java.util.List;

public interface IMenuService {
    List<MenuBar> menuListByRoleIds(List<Long> roleIds);
}
