package com.manage.kernel.core.anyone.service.impl;

import com.manage.base.database.enums.NewsStatus;
import com.manage.base.supplier.Pair;
import com.manage.base.utils.StringHandler;
import com.manage.kernel.core.anyone.service.IAnyNewsService;
import com.manage.kernel.core.model.vo.NewsDetailVo;
import com.manage.kernel.core.model.vo.NewsVo;
import com.manage.kernel.jpa.entity.News;
import com.manage.kernel.jpa.entity.NewsAttach;
import com.manage.kernel.jpa.repository.NewsRepo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bert on 17-12-1.
 */
@Service
public class AnyNewsService implements IAnyNewsService {

    @Autowired
    private NewsRepo newsRepo;

    @Override
    @Transactional
    public Pair<List<NewsVo>, Long> findPublishNews(Integer type, PageRequest pageRequest) {
        return findPublishNews(type, pageRequest, null);
    }

    @Override
    @Transactional
    public Pair<List<NewsVo>, Long> findPublishNews(Integer type, PageRequest pageRequest, String searchText) {
        List<NewsVo> newsVos = new ArrayList<>();
        Page<News> pageResult = newsRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (type != null) {
                list.add(cb.equal(root.get("type"), type));
            }
            if (!StringHandler.isEmpty(searchText)) {
                list.add(cb.like(root.get("title"), "%" + searchText + "%"));
            }
            list.add(cb.equal(root.get("status"), NewsStatus.PASS));
            list.add(cb.isNotNull(root.get("publishTime")));
            return cb.and(list.toArray(new Predicate[0]));
        }, pageRequest);

        NewsVo newsVo;
        for (News news : pageResult.getContent()) {
            newsVo = new NewsVo();
            newsVo.setNumber(news.getNumber());
            newsVo.setTitle(news.getTitle());
            newsVo.setImageId(news.getImageId());
            newsVo.setPublishTime(news.getPublishTime());
            newsVo.setSimpleTime(news.getPublishTime());
            newsVos.add(newsVo);
        }
        Pair<List<NewsVo>, Long> pairResult = new Pair<>();
        pairResult.setLeft(newsVos);
        pairResult.setRight(pageResult.getTotalElements());
        return pairResult;
    }

    @Override
    @Transactional
    public NewsDetailVo newsDetail(String number) {
        NewsDetailVo detail = new NewsDetailVo();
        News news = newsRepo.findByNumber(number);
        if (news != null) {
            detail.setImageId(news.getImageId());
            detail.setContent(news.getContent());
            detail.setTitle(news.getTitle());
            NewsDetailVo.Attachment attachment;
            for (NewsAttach attach : news.getAttaches()) {
                attachment = new NewsDetailVo.Attachment();
                attachment.setFileId(attach.getFileId());
                attachment.setFileName(attach.getFileName());
                detail.getAttachments().add(attachment);
            }
        }
        return detail;
    }
}
