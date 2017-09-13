package com.otms.support.kernel.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otms.support.supplier.serializer.LocalDateTimeDeserializer;
import com.otms.support.supplier.serializer.LocalDateTimeSerializer;
import org.joda.time.LocalDateTime;

public class JobSheetEventDto {
    private String mark;
    private Long eventId;
    private String jobSheetNumber;
    private String externalShipmentId;
    private Integer eventType;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventTimeBegin;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventTimeEnd;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getJobSheetNumber() {
        return jobSheetNumber;
    }

    public void setJobSheetNumber(String jobSheetNumber) {
        this.jobSheetNumber = jobSheetNumber;
    }

    public String getExternalShipmentId() {
        return externalShipmentId;
    }

    public void setExternalShipmentId(String externalShipmentId) {
        this.externalShipmentId = externalShipmentId;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public LocalDateTime getEventTimeBegin() {
        return eventTimeBegin;
    }

    public void setEventTimeBegin(LocalDateTime eventTimeBegin) {
        this.eventTimeBegin = eventTimeBegin;
    }

    public LocalDateTime getEventTimeEnd() {
        return eventTimeEnd;
    }

    public void setEventTimeEnd(LocalDateTime eventTimeEnd) {
        this.eventTimeEnd = eventTimeEnd;
    }
}
