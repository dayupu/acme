package com.manage.kernel.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertySupplier {


    @Value("${file.temporary.dir}")
    private String fileTempDir;

    @Value("${file.upload.local}")
    private String fileUploadLocal;

    public String getFileUploadLocal() {
        return fileUploadLocal;
    }

    public void setFileUploadLocal(String fileUploadLocal) {
        this.fileUploadLocal = fileUploadLocal;
    }

    public String getFileTempDir() {
        return fileTempDir;
    }

    public void setFileTempDir(String fileTempDir) {
        this.fileTempDir = fileTempDir;
    }
}
