package com.manage.kernel.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertySupplier {

    @Value("${upload.images.dir}")
    private String uploadImagesDir;

    @Value("${upload.files.dir}")
    private String uploadFilesDir;

    @Value("${resource.image.access.url}")
    private String imageAccessUrl;

    @Value("${resource.file.access.url}")
    private String fileAccessUrl;

    public String getUploadImagesDir() {
        return uploadImagesDir;
    }

    public String getImageAccessUrl() {
        return imageAccessUrl;
    }

    public String getUploadFilesDir() {
        return uploadFilesDir;
    }

    public String getFileAccessUrl() {
        return fileAccessUrl;
    }
}
