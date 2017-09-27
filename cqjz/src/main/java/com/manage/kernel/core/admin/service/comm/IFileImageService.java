package com.manage.kernel.core.admin.service.comm;

import com.manage.kernel.basic.model.ImageResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bert on 2017/9/1.
 */
public interface IFileImageService {

    public ImageResult uploadImage(MultipartFile multipartFile);
}
