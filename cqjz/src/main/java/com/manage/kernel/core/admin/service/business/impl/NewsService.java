package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.database.enums.NewsStatus;
import com.manage.base.exception.NewsNotFoundException;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.utils.CoreUtil;
import com.manage.base.utils.StringUtil;
import com.manage.kernel.core.admin.apply.dto.NewsDto;
import com.manage.kernel.core.admin.apply.parser.NewsParser;
import com.manage.kernel.core.admin.service.business.INewsService;
import com.manage.kernel.jpa.news.entity.News;
import com.manage.kernel.jpa.news.repository.NewsRepo;
import com.manage.kernel.spring.comm.ServiceBase;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-8-25.
 */
@Service
public class NewsService extends ServiceBase implements INewsService {

    @Autowired
    private NewsRepo newsRepo;

    @Override
    @Transactional
    public NewsDto saveNews(NewsDto newsDto) {

        News news;
        if (newsDto.getId() != null) {
            news = newsRepo.findOne(newsDto.getId());
            if (news == null) {
                throw new NewsNotFoundException();
            }
            setNewsInfo(news, newsDto);
            news.setUpdatedAt(LocalDateTime.now());
            news.setUpdatedUser(currentUser());
        } else {
            news = new News();
            news.setNumber(CoreUtil.nextRandomID());
            setNewsInfo(news, newsDto);
            news.setHits(0);
            news.setCreatedAt(LocalDateTime.now());
            news.setCreatedUser(currentUser());
        }
        news = newsRepo.save(news);
        return NewsParser.toDto(news);
    }

    @Override
    @Transactional
    public NewsDto detail(String number) {
        News news = newsRepo.findByNumber(number);
        if (news == null) {
            return null;
        }
        return NewsParser.toDto(news);
    }

    @Override
    @Transactional
    public PageResult pageList(PageQuery page, NewsDto query) {
        Page<News> userPage = newsRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("createdBy"), currentUser().getId()));
            if (StringUtil.isNotBlank(query.getTitle())) {
                list.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
            }
            if (StringUtil.isNotNull(query.getType())) {
                list.add(cb.equal(root.get("type"), query.getType()));
            }
            if (StringUtil.isNotNull(query.getStatus())) {
                list.add(cb.equal(root.get("status"), query.getStatus()));
            }
            if (StringUtil.isNotNull(query.getCreatedAt())) {
                list.add(cb.greaterThanOrEqualTo(root.get("createdAt").as(LocalDateTime.class),
                        query.getCreatedAt()));
            }
            if (StringUtil.isNotNull(query.getCreatedAtEnd())) {
                list.add(cb.lessThanOrEqualTo(root.get("createdAt").as(LocalDateTime.class),
                        query.getCreatedAtEnd()));
            }

            return cb.and(list.toArray(new Predicate[0]));
        }, page.sortPageDefault("id"));

        PageResult<NewsDto> pageResult = new PageResult<>();
        pageResult.setTotal(userPage.getTotalElements());
        pageResult.setRows(NewsParser.toDtoList(userPage.getContent()));
        return pageResult;
    }

    private void setNewsInfo(News news, NewsDto newsDto) {
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        news.setType(newsDto.getType());
        news.setImageId(newsDto.getImageId());
        news.setSource(newsDto.getSource());
        news.setStatus(NewsStatus.DRAFT);
    }

}
