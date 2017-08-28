package com.manage.kernel.core.admin.view.admin;

import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.extend.enums.FileSource;
import com.manage.base.extend.enums.FileType;
import com.manage.base.supplier.ResponseInfo;
import com.manage.kernel.core.admin.dto.FileDto;
import com.manage.kernel.core.admin.model.FileModel;
import com.manage.kernel.core.admin.service.INewsService;
import com.manage.kernel.core.admin.service.IResourceFileService;
import com.manage.kernel.spring.PropertySupplier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bert on 17-8-25.
 */
@RestController
@RequestMapping("/admin/news")
public class NewsController {

    private final static Logger LOGGER = LogManager.getLogger(NewsController.class);

    @Autowired
    private INewsService newsService;

    @Autowired
    private IResourceFileService fileService;

    @PostMapping("upload")
    public ResponseInfo upload(@RequestParam("file") MultipartFile file) {
        ResponseInfo response = new ResponseInfo();
        try {
            FileModel fileModel = new FileModel(FileSource.NEWS);
            fileModel.setFileName(file.getOriginalFilename());
            fileModel.setFileSize(file.getSize());
            fileModel.setInputStream(file.getInputStream());
            fileModel.setFileType(FileType.IMAGE);
            FileDto fileDto = fileService.upload(fileModel);
            response.wrapSuccess(fileDto);
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }
}
