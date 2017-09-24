package com.manage.kernel.jpa.news.entity;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by bert on 2017/9/24.
 */

@Entity
@Table(name = "confirm_discrepancy_event")
public class EventPush {

    @Id
    private Long id;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "erp_number")
    private String erpNumber;
    @Column(name = "added_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime addedAt;
    @Column(name = "expired_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime expiredAt;
    @Column(name = "event_push_url")
    private String eventPushUrl;
    @Column(name = "event_max_push_retry")
    private Integer eventMaxPushRetry;
    @Column(name = "event_push_retry_count")
    private Integer eventPushRetryCount;
    @Column(name = "event_last_push_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime eventLastPushAt;
    @Column(name = "event_pushed")
    private Boolean eventPushed;
    @Column(name = "event_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime eventTime;
    @Column(name = "request_body")
    private String requestBody;
    @Column(name = "response")
    private String response;
    @Column(name = "clientId")
    private Long clientId;
    @Column(name = "ownerId")
    private Long ownerId;
    @Column(name = "order_discrepancy_id")
    private Long orderDiscrepancyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getEventPushUrl() {
        return eventPushUrl;
    }

    public void setEventPushUrl(String eventPushUrl) {
        this.eventPushUrl = eventPushUrl;
    }

    public Integer getEventMaxPushRetry() {
        return eventMaxPushRetry;
    }

    public void setEventMaxPushRetry(Integer eventMaxPushRetry) {
        this.eventMaxPushRetry = eventMaxPushRetry;
    }

    public Integer getEventPushRetryCount() {
        return eventPushRetryCount;
    }

    public void setEventPushRetryCount(Integer eventPushRetryCount) {
        this.eventPushRetryCount = eventPushRetryCount;
    }

    public LocalDateTime getEventLastPushAt() {
        return eventLastPushAt;
    }

    public void setEventLastPushAt(LocalDateTime eventLastPushAt) {
        this.eventLastPushAt = eventLastPushAt;
    }

    public Boolean getEventPushed() {
        return eventPushed;
    }

    public void setEventPushed(Boolean eventPushed) {
        this.eventPushed = eventPushed;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getOrderDiscrepancyId() {
        return orderDiscrepancyId;
    }

    public void setOrderDiscrepancyId(Long orderDiscrepancyId) {
        this.orderDiscrepancyId = orderDiscrepancyId;
    }
}
