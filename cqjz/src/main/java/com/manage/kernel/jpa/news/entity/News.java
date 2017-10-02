package com.manage.kernel.jpa.news.entity;

import com.manage.base.database.enums.NewsStatus;
import com.manage.base.database.enums.NewsType;
import com.manage.base.database.enums.Status;
import com.manage.kernel.jpa.news.base.EntityBase;
import com.manage.kernel.jpa.news.base.Process;
import com.manage.kernel.jpa.news.base.StatusBase;

import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "news")
@SequenceGenerator(name = "seq_news", sequenceName = "seq_news", allocationSize = 1)
public class News extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_news")
    private Long id;

    @Column(name = "number", nullable = false, length = 50, unique = true)
    private String number;

    @Column(name = "type", nullable = false)
    @Type(type = "com.manage.base.database.model.DBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.database.enums.NewsType")})
    private NewsType type;

    @Column(name = "title", nullable = false, columnDefinition = "text")
    private String title;

    @Column(name = "source", length = 200)
    private String source;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "hits")
    private Integer hits = 0;

    @Embedded
    private Process process;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "status", length = 2)
    @Type(type = "com.manage.base.database.model.DBEnumType",
            parameters = {@Parameter(name = "enumClass", value = "com.manage.base.database.enums.NewsStatus")})
    private NewsStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "news")
    private List<NewsAttach> attaches;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public NewsType getType() {
        return type;
    }

    public void setType(NewsType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public List<NewsAttach> getAttaches() {
        return attaches;
    }

    public void setAttaches(List<NewsAttach> attaches) {
        this.attaches = attaches;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public NewsStatus getStatus() {
        return status;
    }

    public void setStatus(NewsStatus status) {
        this.status = status;
    }
}
