package com.manage.news.spring.aspect;

import com.manage.base.utils.SpringUtils;
import com.manage.base.exceptions.AuthorizedException;
import com.manage.base.exceptions.ApiExeception;
import com.manage.base.utils.WebUtils;
import com.manage.news.token.TokenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class TokenAuthenticationAspect extends AspectBase {

    private static final Logger LOGGER = LogManager.getLogger(TokenAuthenticationAspect.class);
    private static final String HEADER_TOKEN = "token";

    private TokenService tokenService;

    @Pointcut(value = "execution(* com.manage.news.core.admin..*(..)) && @annotation(com.manage.news.spring.annotation.TokenAuthentication)")
    public void doAuthorization() {

    }

    @Before("doAuthorization()")
    public void doBefore(JoinPoint point) throws ApiExeception {

        try {
            HttpServletRequest request = getRequest();
            String tokenId = request.getHeader(HEADER_TOKEN);
            if (StringUtils.isEmpty(tokenId)) {
                throw new AuthorizedException();
            }

            if (!tokenService.isValid(tokenId, WebUtils.remoteIP(request))) {
                throw new AuthorizedException();
            }

            tokenService.extendTTL(tokenId);
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
