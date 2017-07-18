package com.manage.news.core.admin.api;

import com.manage.base.bean.ResponseInfo;
import com.manage.base.enums.Privilege;
import com.manage.base.enums.PrivilegeGroup;
import com.manage.base.enums.ResponseEnum;
import com.manage.cache.bean.TokenUser;
import com.manage.news.core.admin.service.UserService;
import com.manage.news.dto.UserDto;
import com.manage.news.spring.BaseServiceApi;
import com.manage.news.spring.annotation.UserPermission;
import com.manage.news.spring.annotation.UserPermissionGroup;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "User", tags = "User manage")
@RequestMapping(value = "/admin/user", consumes = "application/json", produces = "application/json")
@UserPermissionGroup(PrivilegeGroup.USER)
public class UserApi extends BaseServiceApi {

    @Autowired
    private UserService userService;

    @PostMapping("/test")
    @UserPermission(Privilege.TEST)
    public ResponseInfo login(HttpServletRequest request, String account, String password) {
        TokenUser tokenUser = new TokenUser();
        tokenUser.setAccount(account);
        ResponseInfo response = new ResponseInfo();
        response.setStatus(ResponseEnum.SUCCESS);
        return response;
    }
}