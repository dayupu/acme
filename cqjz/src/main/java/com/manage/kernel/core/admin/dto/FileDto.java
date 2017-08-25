package com.manage.kernel.core.admin.dto;

import java.io.InputStream;

/**
 * Created by bert on 17-8-25.
 */
public class FileDto {

    private String fileName;
    private String accessUrl;
    private InputStream inputStream;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }
}
