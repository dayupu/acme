package com.manage.news.spring.interceptor;

import com.manage.base.utils.JsonUtils;
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

public class InboundLogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LogManager.getLogger(InboundLogInterceptor.class);
    private static final String line = System.getProperty("line.separator");
    private boolean print = false;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (print) {
            StringBuilder log = new StringBuilder("REQUEST: ").append(line);
            log.append(request.getMethod()).append(" ").append(request.getRequestURL().toString()).append(line);
            log.append("parmaters: ").append(getRequestParams(request)).append(line);
            log.append("haeder: ").append(getRequestHeader(request)).append(line);
            String payload = getRequestPayload(request);
            if (!StringUtils.isEmpty(payload)) {
                log.append("payload:").append(payload);
            }
            LOGGER.info(log);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    private String getRequestHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> header = new HashMap<String, String>();
        String key;
        while (headerNames.hasMoreElements()) {
            key = headerNames.nextElement();
            if ("referer".equals(key) || "cookie".equals(key)) {
                continue;
            }
            header.put(key, request.getHeader(key));
        }
        return JsonUtils.toJsonString(header);
    }

    private String getRequestParams(HttpServletRequest request) {
        return JsonUtils.toJsonString(request.getParameterMap());
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
