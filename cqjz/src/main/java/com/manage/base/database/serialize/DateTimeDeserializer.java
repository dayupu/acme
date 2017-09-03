package com.manage.base.database.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;

/**
 * Created by bert on 2017/8/12.
 */
public class DateTimeDeserializer extends JsonDeserializer<DateTime> {

    private static final String pattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        String dateTime = jsonParser.getText();
        if (dateTime == null || "".equals(dateTime.trim())) {
            return null;
        }

        return DateTime.parse(dateTime, DateTimeFormat.forPattern(pattern));
    }
}

