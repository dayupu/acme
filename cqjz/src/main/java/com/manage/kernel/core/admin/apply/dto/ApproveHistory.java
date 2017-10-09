package com.manage.kernel.core.admin.apply.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.database.serialize.EnumDeserializer;
import com.manage.base.database.serialize.EnumSerializer;
import com.manage.base.database.serialize.LocalDateTimeDeserializer;
import com.manage.base.database.serialize.LocalDateTimeSerializer;
import com.manage.base.database.enums.ActProcess;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 2017/10/8.
 */
public class ApproveHistory {
    private String approveUser;
    private String comment;
    private String taskName;
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(using = EnumDeserializer.class)
    private ActProcess process;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startTime;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ActProcess getProcess() {
        return process;
    }

    public void setProcess(ActProcess process) {
        this.process = process;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
