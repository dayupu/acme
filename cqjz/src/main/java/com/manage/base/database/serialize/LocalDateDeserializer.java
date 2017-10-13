package com.manage.base.database.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;

/**
 * Created by bert on 2017/8/12.
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final String pattern = "yyyy-MM-dd";

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        String dateTime = jsonParser.getText();
        if (dateTime == null || "".equals(dateTime.trim())) {
            return null;
        }

        return LocalDate.parse(dateTime, DateTimeFormat.forPattern(pattern));
    }
}

