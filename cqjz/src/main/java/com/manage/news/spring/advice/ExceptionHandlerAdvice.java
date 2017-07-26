package com.manage.news.spring.advice;

import com.manage.base.bean.ResponseInfo;
import com.manage.base.enums.ResponseStatus;
import com.manage.base.exceptions.BusinessException;
import com.manage.base.utils.JsonUtils;
import com.manage.base.exceptions.AuthorizedException;
import com.manage.base.exceptions.ApiExeception;
import com.manage.news.spring.message.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final String UTF8 = "UTF-8";

    @ExceptionHandler(ApiExeception.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.ACCEPTED)
    public ModelAndView handleApiException(HttpServletRequest request, HttpServletResponse response, ApiExeception e)
            throws Exception {

        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.status = ResponseStatus.ERROR;
        responseInfo.message = Messages.get("global.api.access.error");
        String message = JsonUtils.toJsonString(responseInfo);
        return handleAjaxException(response, message, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(AuthorizedException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView handleAuthorizedException(HttpServletRequest request, HttpServletResponse response,
                                                  AuthorizedException e) throws Exception {
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.status = ResponseStatus.ERROR;
        responseInfo.message = Messages.get("global.api.unauthorized");
        String message = JsonUtils.toJsonString(responseInfo);
        return handleAjaxException(response, message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BusinessException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
    public ModelAndView handleBusinessException(HttpServletRequest request, HttpServletResponse response,
                                                BusinessException e) throws Exception {
        String message = JsonUtils.toJsonString(new ResponseInfo(ResponseStatus.ERROR, e.getMessage()));
        return handleAjaxException(response, message, HttpStatus.OK);
    }

    private ModelAndView handleViewException(String url, String message, String viewName) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", url);
        mav.addObject("message", message);
        mav.addObject("timestamp", new Date());
        mav.setViewName(viewName);
        return mav;
    }

    private ModelAndView handleAjaxException(HttpServletResponse response, String message, HttpStatus status)
            throws IOException {
        response.setCharacterEncoding(UTF8);
        response.setStatus(status.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(message);
        writer.flush();
        return null;
    }
}
