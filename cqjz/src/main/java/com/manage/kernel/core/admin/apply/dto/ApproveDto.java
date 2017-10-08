package com.manage.kernel.core.admin.apply.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.database.serialize.EnumDeserializer;
import com.manage.base.database.serialize.EnumSerializer;
import com.manage.base.enums.ActProcess;

/**
 * Created by bert on 2017/10/8.
 */
public class ApproveDto {

    private String taskId;
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(using = EnumDeserializer.class)
    private ActProcess process;
    private String comment;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public ActProcess getProcess() {
        return process;
    }

    public void setProcess(ActProcess process) {
        this.process = process;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
