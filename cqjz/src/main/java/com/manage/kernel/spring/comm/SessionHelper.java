package com.manage.kernel.spring.comm;

import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.spring.config.security.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bert on 2017/10/6.
 */
public class SessionHelper {

    public static AuthUser authUser() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static AdUser user() {
        AuthUser authUser = authUser();
        return new AdUser(authUser.getId(), authUser.getUsername());
    }

    public static String userAccount() {
        return authUser().getUsername();
    }

    public static HttpServletRequest request() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse response() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
