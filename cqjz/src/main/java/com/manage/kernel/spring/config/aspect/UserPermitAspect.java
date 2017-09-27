package com.manage.kernel.spring.config.aspect;

import com.manage.base.exception.ApiExeception;
import com.manage.base.exception.AuthorizedException;
import com.manage.plugins.cache.CacheManager;
import com.manage.kernel.spring.annotation.UserPermit;
import com.manage.kernel.spring.comm.ServiceBase;
import java.util.List;
import com.manage.kernel.spring.config.security.AuthUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class UserPermitAspect extends ServiceBase {

    private static final Logger LOGGER = LogManager.getLogger(UserPermitAspect.class);

    private CacheManager<Long, List<Long>> cacheManager;

    @Pointcut(value = "execution(* com.manage.kernel.core.admin..*(..)) && @annotation(com.manage.kernel.spring.annotation.UserPermit)")
    public void doPermission() {

    }

    @Before("doPermission()")
    public void doBefore(JoinPoint point) throws ApiExeception {

        try {
            AuthUser authUser =  sessionUser();
            if (authUser == null) {
                throw new AuthorizedException();
            }

            UserPermit permit = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(UserPermit.class);
            List<Long> permitIds;
            boolean allow = false;
            for (Long roleId : authUser.getRoleIds()) {
                permitIds = cacheManager.get(roleId);
                if (permitIds == null || permitIds.isEmpty()) {
                    continue;
                }

                if (permitIds.contains(permit.value())) {
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
