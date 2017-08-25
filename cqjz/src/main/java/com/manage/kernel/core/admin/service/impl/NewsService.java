package com.manage.kernel.core.admin.service.impl;

import com.manage.base.extend.enums.FileSource;
import com.manage.base.extend.enums.FileType;
import com.manage.base.utils.FileUtils;
import com.manage.kernel.core.admin.model.FileModel;
import com.manage.kernel.core.admin.service.INewsService;
import com.manage.kernel.core.admin.service.IResourceFileService;
import com.manage.kernel.spring.PropertySupplier;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bert on 17-8-25.
 */
@Service
public class NewsService implements INewsService {

    @Autowired
    private IResourceFileService resourceFileService;

}
