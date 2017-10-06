package com.manage.kernel.spring.comm;

import com.manage.kernel.jpa.news.entity.User;
import com.manage.kernel.spring.config.security.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceBase {

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    protected AuthUser sessionUser() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    protected User currentUser() {
        AuthUser authUser = sessionUser();
        User user = new User();
        user.setId(authUser.getId());
        user.setAccount(authUser.getUsername());
        return user;
    }
}
