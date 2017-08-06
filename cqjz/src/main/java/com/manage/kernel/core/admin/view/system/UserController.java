package com.manage.kernel.core.admin.view.system;

import com.manage.base.atomic.PageResult;
import com.manage.base.atomic.ResponseInfo;
import com.manage.base.extend.enums.ResponseStatus;
import com.manage.kernel.core.admin.service.IUserService;
import com.manage.kernel.spring.annotation.PageQueryAon;
import com.manage.kernel.spring.entry.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public ResponseInfo getUserList(@PageQueryAon PageQuery pageQuery) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = userService.getUserListByPage(pageQuery);
        response.setStatus(ResponseStatus.SUCCESS);
        response.setContent(result);
        return response;
    }
}
