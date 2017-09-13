package com.otms.support.kernel.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.otms.support.supplier.serializer.LocalDateTimeDeserializer;
import com.otms.support.supplier.serializer.LocalDateTimeSerializer;
import java.util.List;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 17-9-11.
 */
public class OrderEventDto {

    private String mark;

    private Long eventId;

    private String orderNumber;

    private String erpNumber;

    private Integer eventType;

    private Double latitude;

    private Double longitude;

    private Integer loss;

    private Integer damage;

    private String fileNames;

    private String truckPlate;

    private String remark;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventTimeBegin;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventTimeEnd;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

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

    public Integer getLoss() {
        return loss;
    }

    public void setLoss(Integer loss) {
        this.loss = loss;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public String getTruckPlate() {
        return truckPlate;
    }

    public void setTruckPlate(String truckPlate) {
        this.truckPlate = truckPlate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getFileNames() {
        return fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames;
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
