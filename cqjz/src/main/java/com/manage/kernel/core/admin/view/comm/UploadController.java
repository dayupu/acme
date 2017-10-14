package com.manage.kernel.core.admin.view.comm;

import com.manage.base.database.enums.FileSource;
import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.utils.FileUtil;
import com.manage.base.utils.JsonUtil;
import com.manage.kernel.basic.model.ImageResult;

import com.manage.kernel.core.admin.service.comm.IResourceService;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bert on 17-8-25.
 */

@Controller
@RequestMapping("/upload")
public class UploadController {

    private static final Logger LOGGER = LogManager.getLogger(UploadController.class);

    @Autowired
    private IResourceService resourceService;

    @PostMapping(value = "/image/ume")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("upfile") MultipartFile file) {
        try {
            response.setContentType("text/html");
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
            response.setCharacterEncoding("utf-8");
            ImageResult result = uploadImage(file, FileSource.NEWS_BODY);
            response.getWriter().print(JsonUtil.toJson(result));
        } catch (IOException e) {
            LOGGER.error("Upload image Exception", e);
        }
    }

    private ImageResult uploadImage(MultipartFile file, FileSource source) {
        return resourceService.uploadImage(file, source);
    }

}