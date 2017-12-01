package com.manage.kernel.core.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-12-1.
 */
public class TopicHomeVo {

    private Integer code;
    private String name;
    private String imageId;
    List<TopicVo> imgColumns = new ArrayList<>();
    List<TopicVo> columns = new ArrayList<>();

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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public List<TopicVo> getColumns() {
        return columns;
    }

    public void setColumns(List<TopicVo> columns) {
        this.columns = columns;
    }

    public List<TopicVo> getImgColumns() {
        return imgColumns;
    }

    public void setImgColumns(List<TopicVo> imgColumns) {
        this.imgColumns = imgColumns;
    }
}
