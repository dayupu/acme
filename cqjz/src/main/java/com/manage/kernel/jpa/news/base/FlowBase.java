package com.manage.kernel.jpa.news.base;

import com.manage.kernel.jpa.news.entity.ProcessFlow;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public class FlowBase extends StatusBase {

    @Column(name = "execution_id", updatable = false, insertable = false)
    private String executionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "execution_id", referencedColumnName = "execution_id")
    private ProcessFlow processFlow;

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }
}
