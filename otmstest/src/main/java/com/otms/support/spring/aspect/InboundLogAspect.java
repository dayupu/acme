package com.otms.support.spring.aspect;

import com.otms.support.kernel.service.InboundService;
import com.otms.support.spring.annotation.InboundLog;
import com.otms.support.supplier.utils.WebUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Aspect
@Configuration
public class InboundLogAspect {

    private static final Logger LOGGER = LogManager.getLogger(InboundLogAspect.class);

    @Autowired
    private InboundService inboundService;

    @Pointcut(value = "@annotation(com.otms.support.spring.annotation.InboundLog)")
    private void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint point) {

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        InboundLog inboundLog =  methodSignature.getMethod().getAnnotation(InboundLog.class);
        if(inboundLog == null){
            return;
        }

        HttpServletRequest request = getRequest();
        inboundService.save(WebUtil.remoteIP(request), requestPayload(request), inboundLog.value());
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private String requestPayload(HttpServletRequest request) {
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            reader = request.getReader();
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
            return sb.toString();
        } catch (Exception e) {
            LOGGER.error("Get request content error", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
