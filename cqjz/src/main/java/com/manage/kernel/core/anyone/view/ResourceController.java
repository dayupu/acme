package com.manage.kernel.core.anyone.view;

import com.manage.kernel.basic.model.FileResult;
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
            response.setHeader("Cache-Control", "private,no-cache,no-store");
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

    @RequestMapping("/file/{id}")
    public void downloadFile(HttpServletResponse response, @PathVariable("id") String fileId) {
        try {
            FileResult file = resourceService.getFile(fileId);
            if (file == null) {
                LOGGER.error("Not found the fileId: {}", fileId);
                return;
            }

            response.setContentType("application/force-download");// 设置强制下载不打开
            String fileName = new String(file.getOriginalName().getBytes("utf-8"),"iso-8859-1");//解决中文乱码
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            File sourceFile = new File(file.getUrl());
            if (!sourceFile.exists()) {
                LOGGER.error("file not exists: {}", file.getUrl());
                return;
            }

            OutputStream outputStream = response.getOutputStream();
            InputStream in = new FileInputStream(sourceFile);
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
