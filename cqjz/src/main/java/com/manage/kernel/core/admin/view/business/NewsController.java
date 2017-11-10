package com.manage.kernel.core.admin.view.business;

import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.database.enums.FileSource;
import com.manage.base.supplier.msgs.MessageInfos;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.utils.Validators;
import com.manage.kernel.basic.model.ImageResult;
import com.manage.kernel.core.model.dto.NewsDto;
import com.manage.kernel.core.admin.service.business.INewsService;

import com.manage.kernel.core.admin.service.comm.IResourceService;
import com.manage.kernel.core.model.dto.NewsTopicDto;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    private IResourceService resourceService;

    @InboundLog
    @GetMapping("/{number}")
    public ResponseInfo newsDetail(@PathVariable("number") String number) {
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
    @DeleteMapping("/{number}")
    public ResponseInfo dropNews(@PathVariable("number") String number) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notBlank(number);
            newsService.drop(number);
            response.wrapSuccess(null);
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
    public ResponseInfo pageList(@PageQueryAon PageQuery page, @RequestBody NewsDto query) {
        ResponseInfo response = new ResponseInfo();
        response.wrapSuccess(newsService.pageList(page, query));
        return response;
    }

    @PostMapping("/save")
    public ResponseInfo saveNews(@RequestBody NewsDto newsDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notBlank(newsDto.getTitle());
            Validators.notBlank(newsDto.getContent());
            Validators.notNull(newsDto.getType());
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

    @PostMapping("/submit")
    public ResponseInfo submitNews(@RequestBody NewsDto newsDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notBlank(newsDto.getTitle());
            Validators.notBlank(newsDto.getContent());
            Validators.notNull(newsDto.getType());
            NewsDto news = newsService.submitNews(newsDto);
            response.wrapSuccess(news, MessageInfos.SUBMIT_SUCCESS);
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
    public ResponseInfo uploadPicture(@RequestParam("file") MultipartFile file) {
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

    @InboundLog
    @PostMapping("/topic/list")
    public ResponseInfo topicPageList(@PageQueryAon PageQuery page, @RequestBody NewsTopicDto query) {
        ResponseInfo response = new ResponseInfo();
        response.wrapSuccess(newsService.topicPageList(page, query));
        return response;
    }

    @GetMapping("/topic/{code}")
    public ResponseInfo topicDetail(@PathVariable("code") Integer code) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(code);
            NewsTopicDto topicDto = newsService.topicDetail(code);
            response.wrapSuccess(topicDto);
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

    @PostMapping("/topic")
    public ResponseInfo topicSave(@RequestBody NewsTopicDto topicDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notBlank(topicDto.getName());
            topicDto = newsService.saveNewsTopic(topicDto);
            response.wrapSuccess(topicDto, MessageInfos.SAVE_SUCCESS);
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

    @PutMapping("/topic")
    public ResponseInfo topicFullSave(@RequestBody NewsTopicDto topicDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(topicDto.getCode());
            Validators.notEmpty(topicDto.getTopicLines());
            topicDto = newsService.saveNewsTopicLines(topicDto);
            response.wrapSuccess(topicDto, MessageInfos.SAVE_SUCCESS);
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
