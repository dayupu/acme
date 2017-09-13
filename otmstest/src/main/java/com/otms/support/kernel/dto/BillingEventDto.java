package com.otms.support.kernel.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otms.support.supplier.serializer.LocalDateTimeDeserializer;
import com.otms.support.supplier.serializer.LocalDateTimeSerializer;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 17-9-12.
 */
public class BillingEventDto {

    private Long id;

    private String mark;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdOn;

    private String message;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdOnBegin;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdOnEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public LocalDateTime getCreatedOnBegin() {
        return createdOnBegin;
    }

    public void setCreatedOnBegin(LocalDateTime createdOnBegin) {
        this.createdOnBegin = createdOnBegin;
    }

    public LocalDateTime getCreatedOnEnd() {
        return createdOnEnd;
    }

    public void setCreatedOnEnd(LocalDateTime createdOnEnd) {
        this.createdOnEnd = createdOnEnd;
    }
}
