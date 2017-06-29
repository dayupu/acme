package com.magic.acme.assist.restful.user;

import com.magic.acme.assist.common.annotation.Permission;
import com.magic.acme.assist.common.annotation.CurrentUser;
import com.magic.acme.assist.common.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestService {

    @Permission
    @RequestMapping(method = RequestMethod.GET, consumes = "application/json")
    public String test(@CurrentUser User user) {

        System.out.println(user.getName());
        return "SUCCESS";
    }

}
