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

    private static final String HEADER_PAGE_SIZE = "page_size";
    private static final String HEADER_PAGE_NUMBER = "page_number";

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
        String pageSize = webRequest.getHeader(HEADER_PAGE_SIZE);
        String pageNumber = webRequest.getHeader(HEADER_PAGE_NUMBER);
        pageQuery.setPageNumber(strToInteger(pageNumber, 1));
        pageQuery.setPageSize(strToInteger(pageSize, 10));
        return pageQuery;
    }

    private static Integer strToInteger(String value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }
}
