package com.manage.kernel.jpa.entity;

import com.manage.base.database.enums.FlowSource;
import com.manage.kernel.jpa.base.EntityBase;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "flow_process")
@SequenceGenerator(name = "seq_flow_process", sequenceName = "seq_flow_process", allocationSize = 1)
public class FlowProcess extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_flow_process")
    private Long id;

    @Column(name = "source", length = 10)
    @Type(type = "com.manage.base.database.model.VarDBEnumType",
            parameters = {@Parameter(name = "enumClass", value = "com.manage.base.database.enums.FlowSource")})
    private FlowSource source;

    @Column(name = "businessId", unique = true)
    private String businessId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "sub_type")
    private Integer subType;

    @Column(name = "subject", nullable = false, columnDefinition = "text")
    private String subject;

    @Column(name = "news_id", insertable = false, updatable = false)
    private Long newsId;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlowSource getSource() {
        return source;
    }

    public void setSource(FlowSource source) {
        this.source = source;
    }



    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }
}
