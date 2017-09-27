package com.manage.kernel.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertySupplier {


    @Value("${file.temporary.dir}")
    private String fileTempDir;

    @Value("${file.upload.local}")
    private String fileUploadLocal;

    @Value("${file.service.url}")
    private String fileServiceUrl;

    @Value("${file.upload.temporary.path}")
    private String uploadTempPath;

    @Value("${upload.images.dir}")
    private String uploadImagesDir;

    @Value("${resource.image.access.url}")
    private String imageAccessUrl;

    public String getFileUploadLocal() {
        return fileUploadLocal;
    }

    public String getFileTempDir() {
        return fileTempDir;
    }

    public String getFileServiceUrl() {
        return fileServiceUrl;
    }

    public String getUploadTempPath() {
        return uploadTempPath;
    }

    public String getUploadImagesDir() {
        return uploadImagesDir;
    }

    public String getImageAccessUrl() {
        return imageAccessUrl;
    }
}
