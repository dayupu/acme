package com.manage.base.database.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.manage.base.database.model.DBEnum;
import com.manage.base.database.model.VarDBEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by bert on 2017/9/3.
 */
public class EnumDeserializer extends JsonDeserializer<Enum> {

    private static final Logger LOGGER = LogManager.getLogger(EnumDeserializer.class);

    @Override
    public Enum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            String value = jsonParser.getText();
            if (value == null) {
                return null;
            }
            Object obj = jsonParser.getCurrentValue();
            Class enumClass = obj.getClass().getDeclaredField(jsonParser.getCurrentName()).getType();
            if (!enumClass.isEnum()) {
                return null;
            }
            if (DBEnum.class.isAssignableFrom(enumClass)) {
                for (Object enumObj : enumClass.getEnumConstants()) {
                    if (value.equals(String.valueOf(((DBEnum) enumObj).getConstant()))) {
                        return (Enum) enumObj;
                    }
                }
            } else if (VarDBEnum.class.isAssignableFrom(enumClass)) {
                for (Object enumObj : enumClass.getEnumConstants()) {
                    if (value.equals(((VarDBEnum) enumObj).getCode())) {
                        return (Enum) enumObj;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Deserializer the field {} exception", jsonParser.getCurrentName(), e);
        }
        return null;
    }
}
