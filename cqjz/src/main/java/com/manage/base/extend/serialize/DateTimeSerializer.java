package com.manage.base.extend.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by bert on 2017/8/12.
 */
public class DateTimeSerializer extends JsonSerializer<DateTime> {

    private static final String pattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (dateTime == null) {
            jsonGenerator.writeNull();
        }
        jsonGenerator.writeString(dateTime.toString(DateTimeFormat.forPattern(pattern)));
    }
}
