package com.manage.kernel.core.admin.view.comm;

import com.manage.base.utils.JsonUtil;
import com.manage.kernel.core.admin.model.UploadModel;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.kernel.core.admin.service.comm.IFileImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bert on 17-8-25.
 */

@Controller
@RequestMapping("/admin/image")
public class ImageController {

    private static final Logger LOGGER = LogManager.getLogger(ImageController.class);

    @Autowired
    private IFileImageService imageService;

    @PostMapping(value = "/upload")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("upfile") MultipartFile file) {
        try {
            response.setContentType("text/html");
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
            response.setCharacterEncoding("utf-8");
            UploadModel model = imageService.uploadImage(file);
            response.getWriter().print(JsonUtil.toJson(model));
        } catch (IOException e) {
            LOGGER.error("Upload image Exception", e);
        }
    }
}