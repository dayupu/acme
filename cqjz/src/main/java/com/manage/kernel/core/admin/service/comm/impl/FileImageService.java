package com.manage.kernel.core.admin.service.comm.impl;

import com.manage.base.enums.UploadState;
import com.manage.base.utils.FileUtil;
import com.manage.kernel.basic.model.ImageResult;
import com.manage.kernel.core.admin.service.comm.IFileImageService;
import com.manage.kernel.spring.PropertySupplier;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bert on 2017/9/1.
 */
@Service
public class FileImageService implements IFileImageService {

    private static final Logger LOGGER = LogManager.getLogger(FileImageService.class);

    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;

    private static final String[] IMAGE_SUFFIX_TYPES = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

    @Autowired
    private PropertySupplier supplier;

    @Override
    public ImageResult uploadImage(MultipartFile multipartFile) {
        ImageResult imageup = new ImageResult();
        if (multipartFile.getSize() > MAX_IMAGE_SIZE) {
            imageup.setState(UploadState.SIZE);
            return imageup;
        }

        String originName = multipartFile.getOriginalFilename();
        String suffix = FileUtil.suffix(originName);
        if (!checkSuffix(suffix)) {
            imageup.setState(UploadState.TYPE);
            return imageup;
        }

        String fileName = FileUtil.generateName(suffix);
        String url = supplier.getUploadTempPath() + fileName;
        try {
            FileUtil.uploadPublic(multipartFile.getInputStream(), url);
            imageup.setSize(multipartFile.getSize());
            imageup.setName(fileName);
            imageup.setOriginalName(originName);
            imageup.setType(suffix);
            imageup.setUrl(url);
            imageup.setState(UploadState.SUCCESS);
            return imageup;
        } catch (IOException e) {
            LOGGER.error("upload exception", e);
            imageup.setState(UploadState.IO);
        } catch (Exception e) {
            LOGGER.error("upload exception", e);
            imageup.setState(UploadState.UNKNOWN);
        }
        return imageup;
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
