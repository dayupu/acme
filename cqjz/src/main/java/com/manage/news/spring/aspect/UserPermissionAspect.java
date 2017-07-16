package com.manage.news.spring.aspect;

import com.manage.base.exceptions.ApiExeception;
import com.manage.base.exceptions.AuthorizedException;
import com.manage.cache.CacheManager;
import com.manage.cache.TokenManager;
import com.manage.cache.bean.TokenUser;
import com.manage.news.spring.annotation.UserPermission;
import com.manage.news.spring.base.AspectBase;
import com.manage.news.spring.base.SpringConstants;

import java.util.List;

import com.manage.news.spring.security.AuthUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

@Aspect
public class UserPermissionAspect extends AspectBase {

    private static final Logger LOGGER = LogManager.getLogger(UserPermissionAspect.class);

    private CacheManager<String, List<String>> cacheManager;

    @Pointcut(value = "execution(* com.manage.news.core.admin..*(..)) && @annotation(com.manage.news.spring.annotation.UserPermission)")
    public void doPermission() {

    }

    @Before("doPermission()")
    public void doBefore(JoinPoint point) throws ApiExeception {

        try {
            AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (authUser == null) {
                throw new AuthorizedException();
            }

            UserPermission permission = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(UserPermission.class);
            List<String> privileges;
            boolean allow = false;
            for (Long roleId : authUser.getRoleIds()) {
                privileges = cacheManager.get(String.valueOf(roleId));
                if (privileges == null || privileges.isEmpty()) {
                    continue;
                }

                if (privileges.contains(permission.value())) {
                    allow = true;
                    break;
                }
            }

            if (!allow) {
                throw new AuthorizedException();
            }
        } catch (AuthorizedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new ApiExeception();
        }
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
