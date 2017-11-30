package com.manage.kernel.spring.config;

import com.manage.base.utils.StringHandler;
import com.manage.kernel.core.admin.service.activiti.impl.ActIdentityService;
import com.manage.kernel.core.admin.service.business.INewsService;
import com.manage.kernel.spring.PropertySupplier;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by bert on 17-9-27.
 */
@Component
public class PrepareConfig implements InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(PrepareConfig.class);

    @Autowired
    private PropertySupplier supplier;

    @Autowired
    private ActIdentityService actIdentityService;

    @Autowired
    private INewsService newsService;

    @Override
    public void afterPropertiesSet() throws Exception {
        initDirs();
        LOGGER.info("Cache news topic and type datas");
        newsService.cacheNewsTopics();
    }

    private void initDirs() {
        String imagesDir = supplier.getUploadImagesDir();
        if (StringHandler.isEmpty(imagesDir)) {
            LOGGER.error("upload images dir not configured.");
        }

        String filesDir = supplier.getUploadFilesDir();
        if (StringHandler.isEmpty(filesDir)) {
            LOGGER.error("upload files dir not configured.");
        }

        File imagesDirFile = new File(imagesDir);
        if (!imagesDirFile.exists()) {
            LOGGER.info("create upload images dir: {}", imagesDir);
            imagesDirFile.mkdirs();
        }
        File filesDirFile = new File(filesDir);
        if (!filesDirFile.exists()) {
            LOGGER.info("create upload files dir: {}", imagesDir);
            filesDirFile.mkdirs();
        }

    }
}
