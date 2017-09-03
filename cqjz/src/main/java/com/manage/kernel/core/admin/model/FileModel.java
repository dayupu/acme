package com.manage.kernel.core.admin.model;

import com.manage.base.database.enums.FileSource;
import com.manage.base.database.enums.FileType;
import java.io.InputStream;

/**
 * Created by bert on 17-8-25.
 */
public class FileModel {

    private String fileName;
    private FileType fileType;
    private FileSource fileSource;
    private InputStream inputStream;
    private Long fileSize;

    public FileModel(FileSource source) {
        this.fileSource = source;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public FileSource getFileSource() {
        return fileSource;
    }

    public void setFileSource(FileSource fileSource) {
        this.fileSource = fileSource;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
