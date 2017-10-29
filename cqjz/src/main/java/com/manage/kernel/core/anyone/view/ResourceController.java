package com.manage.kernel.core.anyone.view;

import com.manage.kernel.basic.model.ImageResult;
import com.manage.kernel.core.admin.service.comm.IResourceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by bert on 2017/9/26.
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    private static final Logger LOGGER = LogManager.getLogger(ResourceController.class);

    @Autowired
    private IResourceService resourceService;

    @RequestMapping("/image/{id}")
    public void showImage(HttpServletResponse response, @PathVariable("id") String imageId) {
        try {
            ImageResult image = resourceService.getImage(imageId);
            if (image == null) {
                LOGGER.error("Not found the imageId: {}", imageId);
                return;
            }
            response.setContentType("image/" + image.getType());
            response.setCharacterEncoding("utf-8");
            File imageFile = new File(image.getUrl());
            if (!imageFile.exists()) {
                LOGGER.error("image not exists: {}", image.getUrl());
                return;
            }

            OutputStream outputStream = response.getOutputStream();
            InputStream in = new FileInputStream(imageFile);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
