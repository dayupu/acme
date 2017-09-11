package com.otms.support.spring.resolver;

import com.otms.support.spring.annotation.PageQueryAon;
import com.otms.support.spring.model.PageQuery;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class PageQueryHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String HEADER_PAGE_SIZE = "page_size";
    private static final String HEADER_PAGE_NUMBER = "page_number";
    private static final String HEADER_SORT_FIELD = "sort_field";
    private static final String HEADER_SORT_DIRECTION = "sort_direction";

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
        pageQuery.setPageNumber(strToInteger(webRequest.getHeader(HEADER_PAGE_NUMBER), 1));
        pageQuery.setPageSize(strToInteger(webRequest.getHeader(HEADER_PAGE_SIZE), 10));
        pageQuery.setSortField(webRequest.getHeader(HEADER_SORT_FIELD));
        pageQuery.setSortDirection(webRequest.getHeader(HEADER_SORT_DIRECTION));
        return pageQuery;
    }

    private static Integer strToInteger(String value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }
}
