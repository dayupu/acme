package com.manage.news.jpa.kernel.entity;


import com.manage.base.converter.FlowStatusAttributeConverter;
import com.manage.base.enums.FlowStatus;

import com.manage.news.jpa.kernel.base.FlowBase;
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
@Table(name = "process_flow")
public class ProcessFlow{

    @Id
    @Column(name = "execution_id", updatable = false, insertable = false)
    private String executionId;

    @Column(name = "process_id", updatable = false, insertable = false)
    private String processId;

    @Column(name = "state", nullable = false, length = 3)
    @Convert(converter = FlowStatusAttributeConverter.class)
    private FlowStatus status;

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

    @Column(name = "updated_by")
    private Long udpatedBy;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
