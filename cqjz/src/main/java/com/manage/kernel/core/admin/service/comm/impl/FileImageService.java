package com.manage.kernel.core.admin.service.comm.impl;

import com.manage.base.enums.UploadState;
import com.manage.base.utils.FileUtil;
import com.manage.kernel.core.admin.model.UploadModel;
import com.manage.kernel.core.admin.service.comm.IFileImageService;
import com.manage.kernel.spring.PropertySupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bert on 2017/9/1.
 */
@Service
public class FileImageService implements IFileImageService {

    @Autowired
    private PropertySupplier supplier;

    @Override
    public UploadModel uploadImage(MultipartFile multipartFile) {
        UploadModel model = new UploadModel();
        String originName = multipartFile.getOriginalFilename();
        String suffix = FileUtil.fileSuffix(originName);
        String fileName = FileUtil.generateName(suffix);
        String url = supplier.getUploadTempPath() + fileName;
        try {
            FileUtil.uploadPublic(multipartFile.getInputStream(), url);
            model.setSize(multipartFile.getSize());
            model.setName(fileName);
            model.setOriginalName(originName);
            model.setType(suffix);
            model.setUrl(url);
            model.setState(UploadState.SUCCESS);
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            model.setState(UploadState.UNKNOWN);
        }

        return model;
    }
}
