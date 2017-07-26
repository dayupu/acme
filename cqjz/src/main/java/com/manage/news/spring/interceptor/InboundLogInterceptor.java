package com.manage.news.spring.interceptor;

import com.google.common.collect.Lists;
import com.manage.base.common.MessageLogging;
import com.manage.base.utils.JsonUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class InboundLogInterceptor extends MessageLogging {

    private static final Logger LOGGER = LogManager.getLogger(InboundLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (!LOGGER.isInfoEnabled()) {
            return true;
        }

        StringBuilder builder = new StringBuilder(line);
        appendLine(builder, request.getMethod(), request.getRequestURL().toString());
        List<String> queryParamStrings = new ArrayList<String>();
        for (Map.Entry<String, String[]> param : request.getParameterMap().entrySet()) {
            queryParamStrings.add(keyValuePair(param.getKey(), wrap(join(Lists.newArrayList(param.getValue())))));
        }
        appendLine(builder, "QueryParams", join(queryParamStrings));

        String key;
        List<String> headerStrings = new ArrayList<String>();
        Enumeration<String> requestHeaders = request.getHeaderNames();
        while (requestHeaders.hasMoreElements()) {
            key = requestHeaders.nextElement();
            headerStrings.add(keyValuePair(key, wrap(request.getHeader(key))));
        }
        appendLine(builder, "Headers", join(headerStrings));

        String payload = getRequestPayload(request);
        appendLine(builder, "Payload", payload);
        LOGGER.info(builder.toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        if (!LOGGER.isInfoEnabled()) {
            return;
        }

        StringBuilder builder = new StringBuilder(line);
        appendLine(builder, "Status", response.getStatus());

        LOGGER.info(builder.toString());
    }



    private String getRequestPayload(HttpServletRequest request) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
            return sb.toString();
        } catch (Exception e) {
            LOGGER.error("Get request content error", e);
        }
        return null;
    }
}
