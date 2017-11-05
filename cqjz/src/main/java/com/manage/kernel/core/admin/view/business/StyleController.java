package com.manage.kernel.core.admin.view.business;

import com.manage.base.database.enums.FileSource;
import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.kernel.basic.model.ImageResult;
import com.manage.kernel.core.admin.service.business.IStyleService;
import com.manage.kernel.core.admin.service.comm.IResourceService;
import com.manage.kernel.core.model.dto.StyleDto;
import com.manage.kernel.core.model.dto.SuperstarDto;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bert on 17-10-16.
 */
@RestController
@RequestMapping("/admin/jz/style")
public class StyleController {

    private static final Logger LOGGER = LogManager.getLogger(StyleController.class);

    @Autowired
    private IStyleService styleService;

    @Autowired
    private IResourceService resourceService;

    @InboundLog
    @PostMapping()
    public ResponseInfo saveStyle(@RequestBody StyleDto styleDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            StyleDto result = styleService.saveStyle(styleDto);
            response.wrapSuccess(result, MessageInfos.SAVE_SUCCESS);
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

    @InboundLog
    @PostMapping("/list")
    public ResponseInfo styleList(@PageQueryAon PageQuery page, @RequestBody StyleDto query) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = styleService.pageList(page, query);
        response.wrapSuccess(result);
        return response;
    }

    @PostMapping("/image")
    public ResponseInfo uploadImage(@RequestParam("file") MultipartFile file) {
        ResponseInfo response = new ResponseInfo();
        try {
            ImageResult result = resourceService.uploadImage(file, FileSource.JZ_STYLE);
            response.wrapSuccess(result);
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
