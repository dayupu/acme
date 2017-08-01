package com.manage.kernel.spring.config.resolver;

import com.manage.kernel.spring.annotation.AuthUserAon;
import com.manage.kernel.spring.annotation.PageQueryAon;
import com.manage.kernel.spring.entry.PageQuery;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by bert on 17-8-1.
 */
public class PageQueryHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterAnnotation(PageQueryAon.class) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        PageQuery pageQuery = new PageQuery();
        return pageQuery;
    }
}
