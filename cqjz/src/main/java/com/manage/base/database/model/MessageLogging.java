package com.manage.base.database.model;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public abstract class MessageLogging extends HandlerInterceptorAdapter {

    protected static final String line = System.getProperty("line.separator");

    protected void appendLine(StringBuilder builder, String key, Object value) {
        builder.append(key).append(": ").append(value).append(line);
    }

    protected String join(List<String> headers) {
        return StringUtils.join(headers, ",");
    }

    protected String keyValuePair(String key, String value) {
        return key + "=" + value;
    }

    protected String wrap(String content) {
        return "[" + content + "]";
    }
}
