package com.manage.kernel.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertySupplier {

    @Value("${upload.images.dir}")
    private String uploadImagesDir;

    @Value("${resource.image.access.url}")
    private String imageAccessUrl;

    public String getUploadImagesDir() {
        return uploadImagesDir;
    }

    public String getImageAccessUrl() {
        return imageAccessUrl;
    }
}
