package com.manage.kernel.core.admin.service.comm;

import com.manage.kernel.core.admin.apply.dto.FileDto;
import com.manage.kernel.basic.model.FileModel;

/**
 * Created by bert on 17-8-25.
 */
public interface IResourceFileService {

    public FileDto upload(FileModel fileModel);

    public FileDto getImage(String fileId);
}
