package com.manage.kernel.core.admin.view.news;

import com.manage.base.extend.enums.FileSource;
import com.manage.base.extend.enums.FileType;
import com.manage.base.supplier.ResponseInfo;
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
            fileService.upload(fileModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("/show")
    public ResponseInfo showImage(HttpServletRequest request) throws Exception {
        ResponseInfo response = new ResponseInfo();

        request.getServletContext().getRealPath("uploads");
        return response;
    }

    ;
}
