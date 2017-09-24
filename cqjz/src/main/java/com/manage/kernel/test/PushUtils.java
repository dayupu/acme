package com.manage.kernel.test;

import java.io.UnsupportedEncodingException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by chien on 11/8/16.
 */
public class PushUtils {

    private static final Logger LOGGER = LogManager.getLogger(PushUtils.class);

    public static CloseableHttpClient createHttpClient() {

        HttpClientBuilder builder = HttpClientBuilder.create().useSystemProperties();

        try {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5 * 1000).setSocketTimeout(60 * 1000)
                    .setConnectionRequestTimeout(5 * 1000).build();
            builder.setDefaultRequestConfig(requestConfig);
        } catch (Exception e) {
            LOGGER.error("create httpclient exception", e);
        }
        return builder.build();
    }

    public static void closeHttpClient(CloseableHttpClient httpClient) {
        try {
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (Exception e) {
            LOGGER.error("httpclient close exception", e);
        }
    }

    // 加密
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    // 解密
    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
