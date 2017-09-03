package com.manage.kernel.core.admin.service.comm;

import com.manage.kernel.core.admin.dto.FileDto;
import com.manage.kernel.core.admin.model.FileModel;

/**
 * Created by bert on 17-8-25.
 */
public interface IResourceFileService {

    public FileDto upload(FileModel fileModel);

    public FileDto getImage(String fileId);
}
