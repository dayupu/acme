package com.manage.kernel.core.admin.service.business.impl;

import com.manage.kernel.core.admin.service.business.INewsService;
import com.manage.kernel.core.admin.service.comm.IResourceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bert on 17-8-25.
 */
@Service
public class NewsService implements INewsService {

    @Autowired
    private IResourceFileService resourceFileService;

}
