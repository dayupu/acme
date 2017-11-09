package com.manage.kernel.jpa.entity;

import com.manage.base.act.support.ActFlowInfo;
import com.manage.base.act.support.ActFlowSupport;
import com.manage.base.database.enums.FlowSource;
import com.manage.base.database.enums.NewsStatus;
import com.manage.base.database.enums.NewsType;
import com.manage.kernel.jpa.base.EntityBase;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "news")
@SequenceGenerator(name = "seq_news", sequenceName = "seq_news", allocationSize = 1)
public class News extends EntityBase implements ActFlowSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_news")
    private Long id;

    @Column(name = "number", nullable = false, length = 50, unique = true)
    private String number;

    @Column(name = "topic")
    private Integer topic;

    @Column(name = "type", nullable = false)
    @Type(type = "com.manage.base.database.model.DBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.database.enums.NewsType") })
    private NewsType type;

    @Column(name = "title", nullable = false, columnDefinition = "text")
    private String title;

    @Column(name = "source", length = 200)
    private String source;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "hits")
    private Integer hits = 0;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "status", length = 2)
    @Type(type = "com.manage.base.database.model.DBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.database.enums.NewsStatus") })
    private NewsStatus status;

    @Column(name = "publish_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime publishTime;

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

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getTopic() {
        return topic;
    }

    public void setTopic(Integer topic) {
        this.topic = topic;
    }

    @Override
    public ActFlowInfo actFlowInfo() {
        ActFlowInfo flowInfo = new ActFlowInfo();
        flowInfo.setFlowSource(FlowSource.NEWS);
        flowInfo.setSubject(this.title);
        flowInfo.setType(this.topic);
        flowInfo.setSubType(this.type.getConstant());
        flowInfo.setTargetId(this.number);
        return flowInfo;
    }
}
