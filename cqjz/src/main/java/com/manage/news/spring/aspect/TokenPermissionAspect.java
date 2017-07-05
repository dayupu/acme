package com.manage.news.spring.aspect;

import com.manage.base.exceptions.ApiExeception;
import com.manage.base.exceptions.AuthorizedException;
import com.manage.news.spring.base.AspectBase;
import com.manage.news.spring.base.SpringConstants;
import com.manage.cache.TokenService;
import com.manage.cache.base.Token;
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

    private TokenService tokenService;

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
            Token token = tokenService.acquireToken(tokenId);

            System.out.println("=========" + token.getIp() + "-------" + token.getId());

        } catch (AuthorizedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(e);
            throw new ApiExeception();
        }
    }

    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }
}
