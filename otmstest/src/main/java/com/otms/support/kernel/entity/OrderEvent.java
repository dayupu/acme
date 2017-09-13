package com.otms.support.kernel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.omg.CORBA.DoubleHolder;

/**
 * Created by bert on 17-9-11.
 */
@Entity
@Table(name = "order_event")
@SequenceGenerator(name = "seq_order_event", sequenceName = "seq_order_event", allocationSize = 1)
public class OrderEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order_event")
    private Long id;

    @Column(name = "mark", length = 50)
    private String mark;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "erp_number")
    private String erpNumber;

    @Column(name = "event_type")
    private Integer eventType;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "loss")
    private Integer loss;

    @Column(name = "damage")
    private Integer damage;

    @Column(name = "truck_plate", length = 50)
    private String truckPlate;

    @Column(name = "remark")
    private String remark;

    @Column(name = "file_names")
    private String fileNames;

    @Column(name = "event_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime eventTime;

    @Column(name = "created_on")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
