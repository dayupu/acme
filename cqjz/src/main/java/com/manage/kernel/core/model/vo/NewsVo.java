package com.manage.kernel.core.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.database.serialize.PublishTimeSerializer;
import com.manage.base.database.serialize.PublishTimeSimpleSerializer;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 2017/10/27.
 */
public class NewsVo {

    private String number;

    private String imageId;

    private String title;

    @JsonSerialize(using = PublishTimeSimpleSerializer.class)
    private LocalDateTime simpleTime;

    @JsonSerialize(using = PublishTimeSerializer.class)
    private LocalDateTime publishTime;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public LocalDateTime getSimpleTime() {
        return simpleTime;
    }

    public void setSimpleTime(LocalDateTime simpleTime) {
        this.simpleTime = simpleTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
