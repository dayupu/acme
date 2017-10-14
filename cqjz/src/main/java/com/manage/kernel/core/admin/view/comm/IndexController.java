package com.manage.kernel.core.admin.view.comm;

import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.enums.ResponseStatus;
import com.manage.kernel.core.admin.apply.dto.MenuNav;
import com.manage.kernel.core.admin.apply.dto.UserDto;
import com.manage.kernel.core.admin.service.system.IMenuService;
import com.manage.kernel.core.admin.service.system.IUserService;
import com.manage.kernel.core.admin.service.system.impl.UserService;
import com.manage.kernel.jpa.repository.AdUserRepo;
import com.manage.kernel.spring.comm.SessionHelper;
import com.manage.kernel.spring.config.security.AuthUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/index")
public class IndexController {

    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);

    @Autowired
    private IMenuService IMenuService;

    @Autowired
    private IUserService userService;

    @GetMapping("/menuList")
    public ResponseInfo getMenuList() {
        ResponseInfo responseInfo = new ResponseInfo();
        AuthUser authUser = SessionHelper.authUser();
        List<MenuNav> menuNavs = IMenuService.menuListByRoleIds(authUser.getRoleIds());
        responseInfo.status = ResponseStatus.SUCCESS;
        responseInfo.content = menuNavs;
        return responseInfo;
    }

    @GetMapping("/user")
    public ResponseInfo getUser() {
        ResponseInfo response = new ResponseInfo();
        try {
            UserDto user = userService.getUser(SessionHelper.user().getAccount());
            response.wrapSuccess(user);
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }
}
