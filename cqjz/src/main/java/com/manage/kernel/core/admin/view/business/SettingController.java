package com.manage.kernel.core.admin.view.business;

import com.manage.base.supplier.page.ResponseInfo;
import com.manage.kernel.core.admin.apply.dto.UserDto;
import com.manage.kernel.core.admin.service.system.IUserService;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.spring.comm.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bert on 17-10-16.
 */
@RestController
@RequestMapping("/admin/setting")
public class SettingController {

    @Autowired
    private IUserService userService;

    @GetMapping("/self")
    public ResponseInfo getSessionUser() {
        ResponseInfo response = new ResponseInfo();
        AdUser user = SessionHelper.user();
        UserDto userDto = userService.getUser(user.getAccount());
        response.wrapSuccess(userDto);
        return response;
    }

}
