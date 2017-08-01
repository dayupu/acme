package com.manage.kernel.core.admin.view;


import com.manage.base.atomic.ResponseInfo;
import com.manage.base.extend.enums.ResponseStatus;
import com.manage.kernel.core.admin.dto.MenuNav;
import com.manage.kernel.core.admin.service.IMenuService;
import com.manage.kernel.spring.config.security.AuthUser;
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
    private IMenuService IMenuService;

    @GetMapping("/menuList")
    public ResponseInfo getMenuList(AuthUser authUser) {
        ResponseInfo responseInfo = new ResponseInfo();
        authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MenuNav> menuNavs = IMenuService.menuListByRoleIds(authUser.getRoleIds());
        responseInfo.status = ResponseStatus.SUCCESS;
        responseInfo.content = menuNavs;
        return responseInfo;
    }
}
