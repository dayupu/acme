package com.manage.kernel.core.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.database.enums.FlowSource;
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
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(using = EnumDeserializer.class)
    private FlowSource flowSource;
    private String businessKey;
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

    public String getApplyUserOrgan() {
        return applyUserOrgan;
    }

    public void setApplyUserOrgan(String applyUserOrgan) {
        this.applyUserOrgan = applyUserOrgan;
    }
}
