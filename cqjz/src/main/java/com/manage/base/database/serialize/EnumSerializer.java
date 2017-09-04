package com.manage.base.database.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.manage.base.database.model.DBEnum;
import com.manage.base.database.model.Localisable;
import com.manage.base.database.model.VarDBEnum;
import com.manage.kernel.spring.comm.Messages;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;

/**
 * Created by bert on 2017/8/12.
 */
public class EnumSerializer extends JsonSerializer<Enum> {

    @Override
    public void serialize(Enum anEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {

        if (anEnum == null) {
            return;
        }

        if (anEnum instanceof DBEnum) {
            jsonGenerator.writeNumber(((DBEnum) anEnum).getConstant());
        } else if (anEnum instanceof VarDBEnum) {
            jsonGenerator.writeString(((VarDBEnum) anEnum).getCode());
        }

        if (anEnum instanceof Localisable) {
            String fieldName = jsonGenerator.getOutputContext().getCurrentName();
            jsonGenerator.writeStringField(fieldName + "Message", Messages.get((Localisable) anEnum));
        }
    }
}
