package com.manage.kernel.core.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.database.serialize.PublishTimeSimpleSerializer;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 2017/10/27.
 */
public class NewsDetailVo {

    private String number;

    private String imageId;

    private String title;

    private String content;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
