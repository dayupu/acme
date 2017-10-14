package com.manage.kernel.core.admin.view.business;

import com.manage.base.enums.ResponseStatus;
import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.admin.apply.dto.SuperstarDto;
import com.manage.kernel.core.admin.apply.dto.WatchDto;
import com.manage.kernel.core.admin.service.business.ISuperStarService;
import com.manage.kernel.core.admin.service.business.IWatchService;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bert on 17-10-13.
 */
@RestController
@RequestMapping("/admin/jz/superstar")
public class SuperstarController {

    private static final Logger LOGGER = LogManager.getLogger(SuperstarController.class);


    @Autowired
    private ISuperStarService superStarService;

    @InboundLog
    @PostMapping("/list")
    public ResponseInfo userListPage(@PageQueryAon PageQuery page, @RequestBody SuperstarDto query) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = superStarService.pageList(page, query);
        response.wrapSuccess(result);
        return response;
    }

    @InboundLog
    @PostMapping
    public ResponseInfo save(@RequestBody SuperstarDto superstarDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notBlank(superstarDto.getYear());
            Validators.notBlank(superstarDto.getMonth());
            Validators.notBlank(superstarDto.getName());
            Validators.notBlank(superstarDto.getHonor());
            Validators.notBlank(superstarDto.getStory());
            superstarDto = superStarService.saveSuperstar(superstarDto);
            response.wrapSuccess(superstarDto, MessageInfos.SAVE_SUCCESS);
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
    @GetMapping("/{id}")
    public ResponseInfo detail(@PathVariable("id") Long id) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(id);
            SuperstarDto result = superStarService.detail(id);
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
