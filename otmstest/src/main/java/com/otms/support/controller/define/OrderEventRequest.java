package com.otms.support.controller.define;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otms.support.supplier.serializer.LocalDateTimeDeserializer;
import com.otms.support.supplier.serializer.LocalDateTimeSerializer;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 17-9-1.
 */
public class OrderEventRequest {

    private List<Event> events;

    public static class Event {
        protected String orderNumber;
        protected String erpNumber;
        protected Integer eventType;
        protected Double latitude;
        protected Double longitude;
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        protected LocalDateTime eventTime;
        protected Long eventId;
        protected Integer damage;
        protected Integer loss;
        protected String location;
        private List<String> fileNames;
        private String remark;
        private Long exceptionId;
        private String truckPlate;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getErpNumber() {
            return erpNumber;
        }

        public void setErpNumber(String erpNumber) {
            this.erpNumber = erpNumber;
        }

        public Integer getEventType() {
            return eventType;
        }

        public void setEventType(Integer eventType) {
            this.eventType = eventType;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public LocalDateTime getEventTime() {
            return eventTime;
        }

        public void setEventTime(LocalDateTime eventTime) {
            this.eventTime = eventTime;
        }

        public Long getEventId() {
            return eventId;
        }

        public void setEventId(Long eventId) {
            this.eventId = eventId;
        }

        public Integer getDamage() {
            return damage;
        }

        public void setDamage(Integer damage) {
            this.damage = damage;
        }

        public Integer getLoss() {
            return loss;
        }

        public void setLoss(Integer loss) {
            this.loss = loss;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public List<String> getFileNames() {
            return fileNames;
        }

        public void setFileNames(List<String> fileNames) {
            this.fileNames = fileNames;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Long getExceptionId() {
            return exceptionId;
        }

        public void setExceptionId(Long exceptionId) {
            this.exceptionId = exceptionId;
        }

        public String getTruckPlate() {
            return truckPlate;
        }

        public void setTruckPlate(String truckPlate) {
            this.truckPlate = truckPlate;
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
