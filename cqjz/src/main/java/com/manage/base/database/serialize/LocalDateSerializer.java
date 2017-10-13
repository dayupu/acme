package com.manage.base.database.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;

/**
 * Created by bert on 2017/8/12.
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private static final String pattern = "yyyy-MM-dd";

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (localDate == null) {
            jsonGenerator.writeNull();
        }
        jsonGenerator.writeString(localDate.toString(DateTimeFormat.forPattern(pattern)));
    }
}
