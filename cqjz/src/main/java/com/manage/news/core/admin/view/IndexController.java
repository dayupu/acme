package com.manage.news.core.admin.view;


import com.manage.base.bean.ResponseInfo;
import com.manage.base.enums.ResponseStatus;
import com.manage.news.core.admin.service.MenuService;
import com.manage.news.dto.MenuDto;
import com.manage.news.spring.security.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/index")
public class IndexController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menuList")
    public ResponseInfo getMenuList(AuthUser authUser) {
        ResponseInfo responseInfo = new ResponseInfo();
        authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MenuDto> menuDtos = menuService.menuListByRoleIds(authUser.getRoleIds());
        responseInfo.status = ResponseStatus.SUCCESS;
        responseInfo.content = menuDtos;
        return responseInfo;
    }
}
