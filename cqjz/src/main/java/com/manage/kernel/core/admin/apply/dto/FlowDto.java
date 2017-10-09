package com.manage.kernel.core.admin.apply.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.act.ActSource;
import com.manage.base.database.enums.ActProcess;
import com.manage.base.database.serialize.EnumDeserializer;
import com.manage.base.database.serialize.EnumSerializer;
import com.manage.base.database.serialize.LocalDateTimeDeserializer;
import com.manage.base.database.serialize.LocalDateTimeSerializer;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 2017/10/6.
 */
public class FlowDto {

    private String taskId;
    private String processId;
    private String subject;
    private String taskName;
    private String nextTaskName;
    private String applyUser;
    private String businessNumber;
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(using = EnumDeserializer.class)
    private ActProcess process;
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(using = EnumDeserializer.class)
    private ActSource businessSource;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime applyTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime applyTimeEnd;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime processTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime processTimeEnd;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime queryTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime queryTimeEnd;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime receiveTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime rejectTime;

    public ActProcess getProcess() {
        return process;
    }

    public void setProcess(ActProcess process) {
        this.process = process;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public ActSource getBusinessSource() {
        return businessSource;
    }

    public void setBusinessSource(ActSource businessSource) {
        this.businessSource = businessSource;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public LocalDateTime getApplyTimeEnd() {
        return applyTimeEnd;
    }

    public void setApplyTimeEnd(LocalDateTime applyTimeEnd) {
        this.applyTimeEnd = applyTimeEnd;
    }

    public LocalDateTime getProcessTime() {
        return processTime;
    }

    public void setProcessTime(LocalDateTime processTime) {
        this.processTime = processTime;
    }

    public LocalDateTime getProcessTimeEnd() {
        return processTimeEnd;
    }

    public void setProcessTimeEnd(LocalDateTime processTimeEnd) {
        this.processTimeEnd = processTimeEnd;
    }

    public LocalDateTime getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(LocalDateTime queryTime) {
        this.queryTime = queryTime;
    }

    public LocalDateTime getQueryTimeEnd() {
        return queryTimeEnd;
    }

    public void setQueryTimeEnd(LocalDateTime queryTimeEnd) {
        this.queryTimeEnd = queryTimeEnd;
    }

    public String getNextTaskName() {
        return nextTaskName;
    }

    public void setNextTaskName(String nextTaskName) {
        this.nextTaskName = nextTaskName;
    }

    public LocalDateTime getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(LocalDateTime rejectTime) {
        this.rejectTime = rejectTime;
    }
}
