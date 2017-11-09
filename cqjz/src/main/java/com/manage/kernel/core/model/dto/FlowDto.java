package com.manage.kernel.core.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.database.enums.ActProcess;
import com.manage.base.database.enums.FlowSource;
import com.manage.base.database.serialize.EnumDeserializer;
import com.manage.base.database.serialize.EnumSerializer;
import com.manage.base.database.serialize.LocalDateTimeDeserializer;
import com.manage.base.database.serialize.LocalDateTimeSerializer;
import com.manage.base.enums.ActStatus;
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
    private String applyUserOrgan;
    private String businessKey;
    private String businessType;
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(using = EnumDeserializer.class)
    private ActProcess process;
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(using = EnumDeserializer.class)
    private ActStatus status;
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(using = EnumDeserializer.class)
    private FlowSource flowSource;
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

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public FlowSource getFlowSource() {
        return flowSource;
    }

    public void setFlowSource(FlowSource flowSource) {
        this.flowSource = flowSource;
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

    public ActStatus getStatus() {
        return status;
    }

    public void setStatus(ActStatus status) {
        this.status = status;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getApplyUserOrgan() {
        return applyUserOrgan;
    }

    public void setApplyUserOrgan(String applyUserOrgan) {
        this.applyUserOrgan = applyUserOrgan;
    }
}
