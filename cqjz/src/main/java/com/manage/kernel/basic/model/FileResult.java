package com.manage.kernel.basic.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.database.serialize.EnumSerializer;
import com.manage.base.enums.UploadState;

/**
 * Created by bert on 17-8-31.
 */
public class FileResult {

    private String fileId;
    private String name;
    private String originalName;
    private Long size;
    @JsonSerialize(using = EnumSerializer.class)
    private UploadState state;
    private String type;
    private String url;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public UploadState getState() {
        return state;
    }

    public void setState(UploadState state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
