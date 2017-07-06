package com.manage.news.spring.aspect;

import com.manage.base.exceptions.AuthorizedException;
import com.manage.base.exceptions.ApiExeception;
import com.manage.base.utils.WebUtils;
import com.manage.cache.TokenManager;
import com.manage.news.spring.base.AspectBase;
import com.manage.news.spring.base.SpringConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class TokenCheckAspect extends AspectBase {

    private static final Logger LOGGER = LogManager.getLogger(TokenCheckAspect.class);
    private static final String HEADER_TOKEN = "java";

    private TokenManager tokenManager;

    @Pointcut(value = "execution(* com.manage.news.core.admin..*(..)) && @annotation(com.manage.news.spring.annotation.TokenCheck)")
    public void doCheck() {

    }

    @Before("doCheck()")
    public void doBefore(JoinPoint point) throws ApiExeception {

        try {
            HttpServletRequest request = getRequest();
            String tokenId = request.getHeader(SpringConstants.HEADER_TOKEN);
            if (StringUtils.isEmpty(tokenId)) {
                throw new AuthorizedException();
            }

            if (!tokenManager.isValid(tokenId, WebUtils.remoteIP(request))) {
                throw new AuthorizedException();
            }

            tokenManager.extendTTL(tokenId);
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
