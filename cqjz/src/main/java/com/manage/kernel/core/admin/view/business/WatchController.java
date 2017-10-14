package com.manage.kernel.core.admin.view.business;

import com.manage.base.enums.ResponseStatus;
import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.admin.apply.dto.NewsDto;
import com.manage.kernel.core.admin.apply.dto.UserDto;
import com.manage.kernel.core.admin.apply.dto.WatchDto;
import com.manage.kernel.core.admin.service.business.IWatchService;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;
import javax.persistence.Column;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bert on 17-10-13.
 */
@RestController
@RequestMapping("/admin/jz/watch")
public class WatchController {

    private static final Logger LOGGER = LogManager.getLogger(WatchController.class);

    @Autowired
    private IWatchService watchService;

    @InboundLog
    @PostMapping("/list")
    public ResponseInfo userListPage(@PageQueryAon PageQuery page, @RequestBody WatchDto query) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = watchService.pageList(page, query);
        response.setStatus(ResponseStatus.SUCCESS);
        response.setContent(result);
        return response;
    }

    @InboundLog
    @PostMapping
    public ResponseInfo saveWatch(@RequestBody WatchDto watchDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(watchDto.getWatchTime());
            Validators.notBlank(watchDto.getLeader());
            Validators.notNull(watchDto.getCaptain());
            Validators.notNull(watchDto.getWorker());
            Validators.notNull(watchDto.getPhone());
            watchService.saveWatch(watchDto);
            response.wrapSuccess(null, MessageInfos.SAVE_SUCCESS);
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
    @PostMapping("/detail")
    public ResponseInfo detail(@RequestBody WatchDto watchDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(watchDto.getWatchTime());
            WatchDto result = watchService.detail(watchDto.getWatchTime());
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
    @DeleteMapping("/{id}")
    public ResponseInfo drop(@PathVariable("id") Long id) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(id);
            watchService.drop(id);
            response.wrapSuccess(null, MessageInfos.SAVE_SUCCESS);
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
