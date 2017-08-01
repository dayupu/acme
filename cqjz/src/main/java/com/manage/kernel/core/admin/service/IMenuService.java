package com.manage.kernel.core.admin.service;

import com.manage.kernel.core.admin.dto.MenuDto;
import com.manage.kernel.core.admin.dto.MenuNav;
import com.manage.kernel.jpa.news.entity.Menu;
import com.manage.kernel.spring.entry.PageQuery;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMenuService {
    List<MenuNav> menuListByRoleIds(List<Long> roleIds);
    Page<Menu> menuList(PageQuery pageQuery);
}
