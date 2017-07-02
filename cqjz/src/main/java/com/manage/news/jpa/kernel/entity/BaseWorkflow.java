package com.manage.news.jpa.kernel.entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public class BaseWorkflow {


    @Column(name = "execution_id", updatable = false, insertable = false)
    private String execution_id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "execution_id", referencedColumnName = "execution_id")
    private Workflow workflow;


    public String getExecution_id() {
        return execution_id;
    }

    public void setExecution_id(String execution_id) {
        this.execution_id = execution_id;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }
}
