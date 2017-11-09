package com.manage.base.act.support;

import com.manage.base.database.enums.FlowSource;

/**
 * Created by bert on 17-11-9.
 */
public class ActFlowInfo {

    private FlowSource flowSource;

    private String targetId;

    private String subject;

    private Integer type;

    private Integer subType;

    public FlowSource getFlowSource() {
        return flowSource;
    }

    public void setFlowSource(FlowSource flowSource) {
        this.flowSource = flowSource;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public String actBusinessId() {
        return ActBusiness.businessKey(this);
    }

}
