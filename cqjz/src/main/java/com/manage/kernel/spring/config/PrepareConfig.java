package com.manage.kernel.spring.config;

import com.manage.base.utils.StringUtil;
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

    @Override
    public void afterPropertiesSet() throws Exception {
        initDirs();
    }

    private void initDirs() {
        String imagesDir = supplier.getUploadImagesDir();
        if (StringUtil.isEmpty(imagesDir)) {
            LOGGER.error("upload images dir not configured.");
        }

        File imagesDirFile = new File(imagesDir);
        if (!imagesDirFile.exists()) {
            LOGGER.info("create upload images dir: {}", imagesDir);
            imagesDirFile.mkdirs();
        }

    }
}
