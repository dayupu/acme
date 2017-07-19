package com.manage.news.core.admin.service.impl;

import com.manage.news.core.admin.service.MenuService;
import com.manage.news.dto.MenuDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Override
    public List<MenuDto> menuListByRoleIds(List<Long> roleIds) {


        return null;
    }
}
