package com.manage.kernel.core.admin.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.extend.serialize.LocalDateTimeDeserializer;
import com.manage.base.extend.serialize.LocalDateTimeSerializer;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 17-8-15.
 */
public class RoleDto {

    private String name;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;
}
