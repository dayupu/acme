package com.manage.kernel.jpa.entity;

import com.manage.base.database.enums.TopicStatus;
import com.manage.kernel.jpa.base.EntityBase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

/**
 * Created by bert on 17-11-8.
 */
@Entity
@Table(name = "news_topic")
@SequenceGenerator(name = "seq_news_topic", sequenceName = "seq_news_topic", allocationSize = 1, initialValue = 100)
public class NewsTopic extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_news_topic")
    private Integer code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parent_code", insertable = false, updatable = false)
    private Integer parentCode;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_code")
    private NewsTopic parent;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "level", length = 2)
    private int level = 0;

    @Column(name = "sequence", length = 3)
    private int sequence = 0;

    @Column(name = "description")
    private String description;

    @Column(name = "status", length = 2)
    @Type(type = "com.manage.base.database.model.DBEnumType",
            parameters = {@Parameter(name = "enumClass", value = "com.manage.base.database.enums.TopicStatus")})
    private TopicStatus status;

    @OneToMany(mappedBy = "parent", cascade = { CascadeType.REMOVE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @OrderBy("sequence asc")
    private List<NewsTopic> topicLines = new ArrayList<>();

    @Column(name = "has_image")
    private Boolean hasImage = Boolean.FALSE;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentCode() {
        return parentCode;
    }

    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }

    public NewsTopic getParent() {
        return parent;
    }

    public void setParent(NewsTopic parent) {
        this.parent = parent;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<NewsTopic> getTopicLines() {
        return topicLines;
    }

    public void setTopicLines(List<NewsTopic> topicLines) {
        this.topicLines = topicLines;
    }

    public Boolean getHasImage() {
        return hasImage;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public TopicStatus getStatus() {
        return status;
    }

    public void setStatus(TopicStatus status) {
        this.status = status;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
