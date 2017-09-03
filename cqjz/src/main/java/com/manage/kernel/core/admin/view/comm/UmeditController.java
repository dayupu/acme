package com.manage.kernel.core.admin.view.comm;

import com.manage.base.utils.JsonUtil;
import com.manage.kernel.core.admin.model.UploadModel;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.manage.kernel.core.admin.service.comm.IFileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Created by bert on 17-8-25.
 */

@Controller
@RequestMapping("/umedit")
public class UmeditController {


    @Autowired
    private IFileImageService imageService;

    @PostMapping(value = "/imageUp")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("upfile") MultipartFile file) {
        try {
            response.setContentType("text/html");
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
            response.setCharacterEncoding("utf-8");
            UploadModel model = imageService.uploadImage(file);
            response.getWriter().print(JsonUtil.toJson(model));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}