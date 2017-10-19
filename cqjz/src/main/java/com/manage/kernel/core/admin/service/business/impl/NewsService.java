package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.act.ActBusiness;
import com.manage.base.act.enums.ActSource;
import com.manage.base.database.enums.NewsStatus;
import com.manage.base.exception.NewsNotFoundException;
import com.manage.base.exception.PrivilegeDeniedException;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.utils.CoreUtil;
import com.manage.base.utils.StringUtil;
import com.manage.kernel.core.admin.apply.dto.NewsDto;
import com.manage.kernel.core.admin.apply.parser.NewsParser;
import com.manage.kernel.core.admin.service.activiti.IActBusinessService;
import com.manage.kernel.core.admin.service.business.INewsService;
import com.manage.kernel.jpa.entity.News;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.jpa.repository.NewsRepo;
import com.manage.kernel.spring.comm.SessionHelper;
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
public class NewsService implements INewsService {

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private IActBusinessService newsActivitiService;

    @Override
    @Transactional
    public NewsDto submitNews(NewsDto newsDto) {

        News news = saveOrUpdateNews(newsDto);
        ActBusiness actBusiness = new ActBusiness();
        actBusiness.setId(news.getId());
        actBusiness.setNumber(news.getNumber());
        actBusiness.setSource(ActSource.NEWS);

        newsActivitiService.submit(actBusiness);
        news.setStatus(NewsStatus.SUBMIT);
        newsRepo.save(news);
        return NewsParser.toDto(news);
    }

    @Override
    @Transactional
    public NewsDto saveNews(NewsDto newsDto) {
        return NewsParser.toDto(saveOrUpdateNews(newsDto));
    }

    private News saveOrUpdateNews(NewsDto newsDto) {
        News news;
        if (newsDto.getId() != null) {
            news = newsRepo.findOne(newsDto.getId());
            if (news == null) {
                throw new NewsNotFoundException();
            }
            if (!news.getStatus().canEdit()) {
                throw new PrivilegeDeniedException();
            }
            setNewsInfo(news, newsDto);
            news.setUpdatedAt(LocalDateTime.now());
            news.setUpdatedUser(SessionHelper.user());
        } else {
            news = new News();
            news.setNumber(CoreUtil.nextRandomID());
            setNewsInfo(news, newsDto);
            news.setHits(0);
            news.setCreatedAt(LocalDateTime.now());
            news.setCreatedUser(SessionHelper.user());
        }
        return newsRepo.save(news);
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
    public void drop(String number) {
        News news = newsRepo.findByNumber(number);
        if (news == null) {
            throw new NewsNotFoundException();
        }

        AdUser user = SessionHelper.user();
        if (StringUtil.notEquals(news.getCreatedBy(), user.getId())) {
            throw new PrivilegeDeniedException();
        }

        if (!news.getStatus().canDrop()) {
            throw new PrivilegeDeniedException();
        }

        news.setStatus(NewsStatus.DELETE);
        news.setUpdatedAt(LocalDateTime.now());
        news.setUpdatedUser(user);
        newsRepo.save(news);
    }

    @Override
    @Transactional
    public PageResult pageList(PageQuery page, NewsDto query) {
        Page<News> userPage = newsRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("createdBy"), SessionHelper.user().getId()));
            if (StringUtil.isNotBlank(query.getTitle())) {
                list.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
            }
            if (StringUtil.isNotNull(query.getType())) {
                list.add(cb.equal(root.get("type"), query.getType()));
            }
            if (StringUtil.isNotNull(query.getStatus())) {
                list.add(cb.equal(root.get("status"), query.getStatus()));
            } else {
                list.add(cb.notEqual(root.get("status"), NewsStatus.DELETE));
            }
            if (StringUtil.isNotNull(query.getCreatedAt())) {
                list.add(cb.greaterThanOrEqualTo(root.get("createdAt").as(LocalDateTime.class), query.getCreatedAt()));
            }
            if (StringUtil.isNotNull(query.getCreatedAtEnd())) {
                list.add(cb.lessThanOrEqualTo(root.get("createdAt").as(LocalDateTime.class), query.getCreatedAtEnd()));
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
        if (newsDto.getType().hasImage()) {
            news.setImageId(newsDto.getImageId());
        } else {
            news.setImageId(null);
        }
        news.setSource(newsDto.getSource());
        news.setStatus(NewsStatus.DRAFT);
    }

    @Override
    @Transactional
    public List<NewsDto> newestNews(AdUser user) {
        PageQuery page = new PageQuery();
        page.setPageNumber(1);
        page.setPageSize(5);
        page.setSortField("approvedTime");
        page.setSortDirection(PageQuery.ORDER_DESC);
        Page<News> newses = newsRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("status"), NewsStatus.PASS));
            return cb.and(list.toArray(new Predicate[0]));
        }, page.sortPage());

        NewsDto newsDto;
        List<NewsDto> newsDtos = new ArrayList<>();
        for (News news : newses.getContent()) {
            newsDto = NewsParser.toDto(news);
            newsDto.setCreatedByOrgan(news.getCreatedUser().getOrganName());
            newsDtos.add(newsDto);
        }
        return newsDtos;
    }
}
