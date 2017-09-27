package com.manage.kernel.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * Created by bert on 17-8-23.
 */
public class EventPushUtil extends PushUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static EventResponse sendPostRequest(String request, String url, String username, String password,
            boolean isAuthorization) {
        CloseableHttpClient client = null;
        try {
            client = createHttpClient();
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(request, "application/json", HTTP.UTF_8));
            if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
                if (isAuthorization) {
                    String base = getBase64(username + ":" + password);
                    post.setHeader("Authorization", "Basic " + base);
                } else {
                    post.setHeader("username", username);
                    post.setHeader("password", password);
                }
            }
            HttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            String entityStr = null;
            if (response.getEntity() != null) {
                entityStr = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            }
            return new EventResponse(statusCode, entityStr);
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            closeHttpClient(client);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}
