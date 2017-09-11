package com.otms.support.kernel.entity;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

/**
 * Created by bert on 17-9-11.
 */
@Entity
@Table(name = "job_sheet_event")
@SequenceGenerator(name = "seq_job_sheet_event", sequenceName = "seq_job_sheet_event", allocationSize = 1)
public class JobSheetEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_job_sheet_event")
    private Long id;

    @Column(name = "mark", length = 50)
    private String mark;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_type")
    private Integer eventType;

    @Column(name = "job_sheet_number")
    private String jobSheetNumber;

    @Column(name = "external_shipment_id")
    private String externalShipmentId;

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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
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

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
