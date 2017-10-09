package com.manage.kernel.core.admin.service.comm.impl;

import com.manage.base.database.enums.FileSource;
import com.manage.base.enums.UploadState;
import com.manage.base.utils.CoreUtil;
import com.manage.base.utils.FileUtil;
import com.manage.kernel.basic.model.ImageResult;
import com.manage.kernel.core.admin.service.comm.IResourceService;
import com.manage.kernel.jpa.entity.ResourceImage;
import com.manage.kernel.jpa.repository.ResourceImageRepo;
import com.manage.kernel.spring.PropertySupplier;
import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bert on 17-9-27.
 */
@Service
public class ResourceService implements IResourceService {

    private static final Logger LOGGER = LogManager.getLogger(ResourceService.class);

    private static final long IMAGE_MAX_SIZE = 5 * 1024 * 1024;
    private static final String[] IMAGE_SUFFIX_TYPES = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

    @Autowired
    private ResourceImageRepo imageRepo;
    @Autowired
    private PropertySupplier supplier;

    @Override
    @Transactional
    public ImageResult uploadImage(MultipartFile file, FileSource source) {
        ImageResult imageResult = new ImageResult();
        if (file.getSize() > IMAGE_MAX_SIZE) {
            imageResult.setState(UploadState.SIZE);
            return imageResult;
        }

        String originName = file.getOriginalFilename();
        String suffix = FileUtil.suffix(originName);
        if (!checkSuffix(suffix)) {
            imageResult.setState(UploadState.TYPE);
            return imageResult;
        }

        String imagesDir = supplier.getUploadImagesDir();
        String imageId = FileUtil.genImageId();
        String dateDir = FileUtil.genDateDir();
        String savePath = FileUtil.joinPath(imagesDir, dateDir);
        long size = file.getSize();
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        String fileName = imageId + suffix;
        try {
            FileUtil.upload(file.getInputStream(), FileUtil.joinPath(savePath, fileName));

            // save resource image
            ResourceImage image = new ResourceImage();
            image.setName(fileName);
            image.setImageId(imageId);
            image.setCreatedAt(LocalDateTime.now());
            image.setDir(imagesDir);
            image.setPath(FileUtil.joinPath(dateDir, fileName));
            image.setSize(size);
            image.setOriginName(originName);
            image.setSuffix(suffix);
            image.setSource(source);
            imageRepo.save(image);

            // ready image result
            imageResult.setImageId(imageId);
            imageResult.setSize(size);
            imageResult.setName(fileName);
            imageResult.setOriginalName(originName);
            imageResult.setType(suffix);
            imageResult.setUrl(CoreUtil.format(supplier.getImageAccessUrl(), imageId));
            imageResult.setState(UploadState.SUCCESS);
            return imageResult;
        } catch (IOException e) {
            LOGGER.error("upload exception", e);
            imageResult.setState(UploadState.IO);
        } catch (Exception e) {
            LOGGER.error("upload exception", e);
            imageResult.setState(UploadState.UNKNOWN);
        }
        return imageResult;
    }

    @Override
    public void uploadFile(MultipartFile file, FileSource source) {

    }

    @Override
    public String imagePath(String imageId) {
        ResourceImage image = imageRepo.findByImageId(imageId);
        if (image == null) {
            return null;
        }
        return FileUtil.joinPath(supplier.getUploadImagesDir(), image.getPath());
    }

    private boolean checkSuffix(String suffix) {
        for (String suff : IMAGE_SUFFIX_TYPES) {
            if (suff.equals(suffix)) {
                return true;
            }
        }
        return false;
    }
}
