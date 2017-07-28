package com.manage.kernel.spring.comm;

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

    protected AuthUser currentUser() {
        return (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
