package com.manage.kernel.core.admin.view.news;

import com.manage.base.database.enums.NewsStatus;
import com.manage.base.database.enums.NewsType;
import com.manage.base.enums.ResponseStatus;
import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.database.enums.FileSource;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.supplier.page.SelectOption;
import com.manage.base.utils.Validators;
import com.manage.kernel.basic.model.ImageResult;
import com.manage.kernel.core.admin.apply.dto.NewsDto;
import com.manage.kernel.core.admin.apply.dto.UserDto;
import com.manage.kernel.core.admin.service.business.INewsService;

import com.manage.kernel.core.admin.service.comm.IResourceService;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;
import com.manage.kernel.spring.comm.Messages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    private IResourceService resourceService;

    @GetMapping("/types")
    public ResponseInfo newsTypes() {
        ResponseInfo response = new ResponseInfo();
        List<SelectOption> options = new ArrayList<>();
        SelectOption<Integer, String> option;
        for (NewsType type : NewsType.values()) {
            option = new SelectOption<>();
            option.setKey(type.getConstant());
            option.setValue(Messages.get(type));
            options.add(option);
        }
        response.wrapSuccess(options);
        return response;
    }

    @GetMapping("/status")
    public ResponseInfo newsStatus() {
        ResponseInfo response = new ResponseInfo();
        List<SelectOption> options = new ArrayList<>();
        SelectOption<Integer, String> option;
        for (NewsStatus type : NewsStatus.values()) {
            if (type == NewsStatus.DELETE) {
                continue;
            }
            option = new SelectOption<>();
            option.setKey(type.getConstant());
            option.setValue(Messages.get(type));
            options.add(option);
        }
        response.wrapSuccess(options);
        return response;
    }

    @InboundLog
    @GetMapping("/{number}")
    public ResponseInfo userDetail(@PathVariable("number") String number) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notBlank(number);
            NewsDto newsDto = newsService.detail(number);
            response.wrapSuccess(newsDto);
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
    public ResponseInfo userListPage(@PageQueryAon PageQuery page, @RequestBody NewsDto query) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = newsService.pageList(page, query);
        response.wrapSuccess(result);
        return response;
    }

    @PostMapping("/save")
    public ResponseInfo saveNews(@RequestBody NewsDto newsDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notBlank(newsDto.getTitle());
            Validators.notBlank(newsDto.getContent());
            Validators.notNull(newsDto.getType());
            if (newsDto.getType().requireImage()) {
                Validators.notBlank(newsDto.getImageId());
            }
            NewsDto news = newsService.saveNews(newsDto);
            response.wrapSuccess(news, MessageInfos.SAVE_SUCCESS);
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

    @PostMapping("/picture")
    public ResponseInfo uploadPic(@RequestParam("file") MultipartFile file) {
        ResponseInfo response = new ResponseInfo();
        try {
            ImageResult result = resourceService.uploadImage(file, FileSource.NEWS_SUMMARY);
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
