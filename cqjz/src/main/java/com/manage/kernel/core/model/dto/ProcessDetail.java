package com.manage.kernel.core.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.act.enums.ActSource;
import com.manage.base.database.serialize.EnumDeserializer;
import com.manage.base.database.serialize.EnumSerializer;
import com.manage.base.database.serialize.LocalDateTimeDeserializer;
import com.manage.base.database.serialize.LocalDateTimeSerializer;
import org.joda.time.LocalDateTime;

import java.util.List;

/**
 * Created by bert on 2017/10/8.
 */
public class ProcessDetail {

    private String processId;
    private String subject;
    private String processType;
    private String businessNumber;
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(using = EnumDeserializer.class)
    private ActSource businessSource;
    private String businessType;
    private String applyUser;
    private String applyUserOrgan;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime applyTime;
    private List<ApproveHistory> approveHistories;
    private boolean canApprove = Boolean.FALSE;

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

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public List<ApproveHistory> getApproveHistories() {
        return approveHistories;
    }

    public void setApproveHistories(List<ApproveHistory> approveHistories) {
        this.approveHistories = approveHistories;
    }

    public boolean isCanApprove() {
        return canApprove;
    }

    public void setCanApprove(boolean canApprove) {
        this.canApprove = canApprove;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getApplyUserOrgan() {
        return applyUserOrgan;
    }

    public void setApplyUserOrgan(String applyUserOrgan) {
        this.applyUserOrgan = applyUserOrgan;
    }
}
