package com.otms.support.controller.define;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otms.support.supplier.serializer.LocalDateTimeDeserializer;
import com.otms.support.supplier.serializer.LocalDateTimeSerializer;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class JobSheetRequest {


    private List<Event> events;

    public static class Event {

        private Long eventId;
        private String jobSheetNumber;
        private String externalShipmentId;
        private Integer eventType;

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime eventTime;

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
    }

    public List<Event> getEvents() {
        if (events == null) {
            events = new ArrayList<>();
        }
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
