package com.manage.kernel.core.admin.service;

import com.manage.base.extend.enums.FileSource;
import com.manage.base.extend.enums.FileType;
import com.manage.kernel.core.admin.dto.FileDto;
import com.manage.kernel.core.admin.model.FileModel;
import java.io.InputStream;

/**
 * Created by bert on 17-8-25.
 */
public interface IResourceFileService {

    public FileDto upload(FileModel fileModel);

    public FileDto getImage(String fileId);
}
