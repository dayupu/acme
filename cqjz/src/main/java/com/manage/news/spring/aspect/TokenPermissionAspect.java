package com.manage.news.spring.aspect;

import com.manage.base.exceptions.ApiExeception;
import com.manage.base.exceptions.AuthorizedException;
import com.manage.cache.CacheManager;
import com.manage.cache.TokenManager;
import com.manage.cache.bean.TokenUser;
import com.manage.news.spring.annotation.TokenPermission;
import com.manage.news.spring.base.AspectBase;
import com.manage.news.spring.base.SpringConstants;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

@Aspect
public class TokenPermissionAspect extends AspectBase {

    private static final Logger LOGGER = LogManager.getLogger(TokenPermissionAspect.class);

    private TokenManager tokenManager;

    private CacheManager<String, List<String>> cacheManager;

    @Pointcut(value = "execution(* com.manage.news.core.admin..*(..)) && @annotation(com.manage.news.spring.annotation.TokenPermission)")
    public void doPermission() {

    }

    @Before("doPermission()")
    public void doBefore(JoinPoint point) throws ApiExeception {

        try {
            String tokenId = getRequest().getHeader(SpringConstants.HEADER_TOKEN);
            if (StringUtils.isEmpty(tokenId)) {
                throw new AuthorizedException();
            }

            TokenUser tokenUser = tokenManager.getTokenUser(tokenId);
            if (tokenUser == null || tokenUser.getRoles() == null) {
                throw new AuthorizedException();
            }

            TokenPermission permission = ((MethodSignature) point.getSignature()).getMethod()
                    .getAnnotation(TokenPermission.class);
            List<String> privileges;
            boolean allow = false;
            for (String roleId : tokenUser.getRoles()) {
                privileges = cacheManager.get(roleId);
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

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
