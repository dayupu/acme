package com.manage.news.jpa.kernel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "news")
public class News extends BaseWorkflow {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "news_number", nullable = false, unique = true, length = 50)
    private String newsNumber;

    @Column(name = "title", nullable = false, columnDefinition = "text")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "contentmin", length = 500)
    private String contentmin;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "dept_code", length = 50)
    private String deptCode;

    @Column(name = "hits")
    private Integer hits = 0;

    @Column(name = "pic_url", length = 50)
    private String picUrl;

    @Column(name = "pic_count")
    private Integer picCount;

    @Column(name = "show_pic_news")
    private Integer showPicNews;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "purview", length = 200)
    private String purview;

    @Column(name = "contribute_tag")
    private Integer contributeTag;

    @Column(name = "contribute_name", length = 50)
    private String contributeName;

    @Column(name = "source", length = 50)
    private String source;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewsNumber() {
        return newsNumber;
    }

    public void setNewsNumber(String newsNumber) {
        this.newsNumber = newsNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentmin() {
        return contentmin;
    }

    public void setContentmin(String contentmin) {
        this.contentmin = contentmin;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getPicCount() {
        return picCount;
    }

    public void setPicCount(Integer picCount) {
        this.picCount = picCount;
    }

    public Integer getShowPicNews() {
        return showPicNews;
    }

    public void setShowPicNews(Integer showPicNews) {
        this.showPicNews = showPicNews;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPurview() {
        return purview;
    }

    public void setPurview(String purview) {
        this.purview = purview;
    }

    public Integer getContributeTag() {
        return contributeTag;
    }

    public void setContributeTag(Integer contributeTag) {
        this.contributeTag = contributeTag;
    }

    public String getContributeName() {
        return contributeName;
    }

    public void setContributeName(String contributeName) {
        this.contributeName = contributeName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
