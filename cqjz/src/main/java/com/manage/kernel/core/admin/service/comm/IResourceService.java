package com.manage.kernel.core.admin.service.comm;

import com.manage.base.database.enums.FileSource;
import com.manage.kernel.basic.model.ImageResult;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bert on 17-9-27.
 */
public interface IResourceService {

    public ImageResult uploadImage(MultipartFile file, FileSource source);

    public void uploadFile(MultipartFile file, FileSource source);

    public String imagePath(String imageId);
}
