package com.manage.news.spring.aspect;

import com.manage.base.exceptions.ApiExeception;
import com.manage.base.exceptions.AuthorizedException;
import com.manage.cache.CacheManager;
import com.manage.cache.TokenManager;
import com.manage.cache.bean.TokenUser;
import com.manage.news.spring.base.AspectBase;
import com.manage.news.spring.base.SpringConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StringUtils;

@Aspect
public class TokenPermissionAspect extends AspectBase {

    private static final Logger LOGGER = LogManager.getLogger(TokenPermissionAspect.class);

    private TokenManager tokenManager;

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

            System.out.println("=========" + tokenUser.getIp() + "-------" + tokenUser.getAccount());

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
}
