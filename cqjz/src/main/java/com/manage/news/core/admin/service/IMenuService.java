package com.manage.news.core.admin.service;

import com.manage.news.dto.MenuDto;

import java.util.List;

public interface IMenuService {
    List<MenuDto> menuListByRoleIds(List<Long> roleIds);
}
