package com.manage.base.database.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;

/**
 * Created by bert on 2017/8/12.
 */
public class PublishTimeSimpleSerializer extends JsonSerializer<LocalDateTime> {

    private static final String pattern = "MM/dd";

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (localDateTime == null) {
            jsonGenerator.writeNull();
        }
        jsonGenerator.writeString(localDateTime.toString(DateTimeFormat.forPattern(pattern)));
    }
}
