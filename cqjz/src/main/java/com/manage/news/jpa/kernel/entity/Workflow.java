package com.manage.news.jpa.kernel.entity;


import com.manage.base.converter.ProcessStateAttributeConverter;
import com.manage.base.enums.ProcessState;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "workflow")
public class Workflow {

    @Id
    @Column(name = "execution_id", unique = true, nullable = false, length = 64)
    private String executionId;

    @Column(name = "process_id", nullable = false, length = 64)
    private String processId;

    @Column(name = "state", nullable = false, length = 3)
    @Convert(converter = ProcessStateAttributeConverter.class)
    private ProcessState state;

    @Column(name = "news_id", insertable = false, updatable = false)
    private Long news_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    private News news;

    @Column(name = "apply_by")
    private String applyBy;

    @Column(name = "apply_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applyOn;

    @Column(name = "last_updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedOn;

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Long getNews_id() {
        return news_id;
    }

    public void setNews_id(Long news_id) {
        this.news_id = news_id;
    }

    public String getApplyBy() {
        return applyBy;
    }

    public void setApplyBy(String applyBy) {
        this.applyBy = applyBy;
    }

    public Date getApplyOn() {
        return applyOn;
    }

    public void setApplyOn(Date applyOn) {
        this.applyOn = applyOn;
    }

    public Date getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }
}
