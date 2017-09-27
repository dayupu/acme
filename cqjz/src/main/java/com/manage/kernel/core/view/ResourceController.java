package com.manage.kernel.core.view;

import com.manage.kernel.core.admin.service.comm.IResourceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        response.setContentType("img/jpeg");
        response.setCharacterEncoding("utf-8");
        try {

            String imagePath = resourceService.imagePath(imageId);
            if (imagePath == null) {
                LOGGER.error("Not found the imageId: {}", imageId);
                return;
            }

            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                LOGGER.error("image not exists: {}", imagePath);
                return;
            }

            OutputStream outputStream = response.getOutputStream();
            InputStream in = new FileInputStream(imageFile);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
