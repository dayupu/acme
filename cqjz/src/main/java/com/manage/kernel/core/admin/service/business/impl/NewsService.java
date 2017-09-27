package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.utils.CoreUtil;
import com.manage.kernel.core.admin.apply.dto.NewsDto;
import com.manage.kernel.core.admin.apply.parser.NewsParser;
import com.manage.kernel.core.admin.service.business.INewsService;
import com.manage.kernel.jpa.news.entity.News;
import com.manage.kernel.jpa.news.repository.NewsRepo;
import com.manage.kernel.spring.comm.ServiceBase;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bert on 17-8-25.
 */
@Service
public class NewsService extends ServiceBase implements INewsService {

    @Autowired
    private NewsRepo newsRepo;

    @Override
    @Transactional
    public NewsDto addNews(NewsDto newsDto) {

        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        news.setType(newsDto.getType());
        news.setNumber(CoreUtil.nextRandomID());
        news.setHits(0);
        news.setSource(newsDto.getSource());
        news.setCreatedAt(LocalDateTime.now());
        news.setCreatedUser(currentUser());
        news = newsRepo.save(news);
        return NewsParser.toNewsDto(news);
    }
}
