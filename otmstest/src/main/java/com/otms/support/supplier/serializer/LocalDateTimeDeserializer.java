package com.otms.support.supplier.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Created by bert on 2017/8/12.
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final String pattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        String dateTime = jsonParser.getText();
        if (dateTime == null || "".equals(dateTime.trim())) {
            return null;
        }

        return LocalDateTime.parse(dateTime, DateTimeFormat.forPattern(pattern));
    }
}

