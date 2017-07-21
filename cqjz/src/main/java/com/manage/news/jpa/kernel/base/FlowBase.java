package com.manage.news.jpa.kernel.base;

import com.manage.news.jpa.kernel.entity.ProcessFlow;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class FlowBase extends CommonBase{


    @Column(name = "execution_id", updatable = false, insertable = false)
    private String execution_id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "execution_id", referencedColumnName = "execution_id")
    private ProcessFlow processFlow;

    public String getExecution_id() {
        return execution_id;
    }

    public void setExecution_id(String execution_id) {
        this.execution_id = execution_id;
    }

    public ProcessFlow getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(ProcessFlow processFlow) {
        this.processFlow = processFlow;
    }
}
