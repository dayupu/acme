package com.manage.kernel.core.admin.view;

import com.manage.base.supplier.ResponseInfo;
import com.manage.kernel.core.admin.dto.FileDto;
import com.manage.kernel.core.admin.service.IResourceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bert on 17-8-25.
 */

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IResourceFileService fileService;

}
