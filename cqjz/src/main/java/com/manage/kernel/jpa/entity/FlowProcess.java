package com.manage.kernel.jpa.entity;

import com.manage.base.database.enums.ApproveRole;
import com.manage.base.database.enums.FlowSource;
import com.manage.base.utils.CoreUtil;
import com.manage.kernel.jpa.base.EntityBase;

import javax.persistence.*;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Column(name = "process_id", unique = true)
    private String processId;

    @Column(name = "business_id", unique = true)
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

    @Column(name = "curr_role")
    @Type(type = "com.manage.base.database.model.VarDBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.database.enums.ApproveRole")})
    private ApproveRole currRole;

    @Column(name = "next_role")
    @Type(type = "com.manage.base.database.model.VarDBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.database.enums.ApproveRole")})
    private ApproveRole nextRole;

    @Column(name = "apply_act_user", insertable = false, updatable = false)
    private String applyActUser;

    @Column(name = "apply_organ_code", insertable = false, updatable = false)
    private String applyOrganCode;

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

    public ApproveRole getCurrRole() {
        return currRole;
    }

    public void setCurrRole(ApproveRole currRole) {
        this.currRole = currRole;
    }

    public ApproveRole getNextRole() {
        return nextRole;
    }

    public void setNextRole(ApproveRole nextRole) {
        this.nextRole = nextRole;
    }

    public String getApplyActUser() {
        return applyActUser;
    }

    public void setApplyActUser(String applyActUser) {
        this.applyActUser = applyActUser;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getApplyOrganCode() {
        return applyOrganCode;
    }

    public void setApplyOrganCode(String applyOrganCode) {
        this.applyOrganCode = applyOrganCode;
    }
}
