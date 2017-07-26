package com.manage.base.common;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
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
        return new StringBuilder().append(key).append("=").append(value).toString();
    }

    protected String wrap(String content) {
        return new StringBuilder().append("[").append(content).append("]").toString();
    }

    protected String getCharset(MediaType mediaType) {
        //        if (mediaType == null || mediaType.getParameters() == null
        //                || mediaType.getParameters().get(MediaType.CHARSET_PARAMETER) == null) {
        //            return WsProperties.DEFAULT_CHARSET;
        //        }
        //        return mediaType.getParameters().get(MediaType.CHARSET_PARAMETER);
        return null;
    }
}
