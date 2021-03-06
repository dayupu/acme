package com.manage.base.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();
    static{
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
