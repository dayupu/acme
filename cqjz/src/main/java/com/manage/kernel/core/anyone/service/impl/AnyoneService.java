package com.manage.kernel.core.anyone.service.impl;

import com.manage.base.database.enums.NewsStatus;
import com.manage.base.database.enums.NewsType;
import com.manage.base.supplier.page.PageQuery;
import com.manage.kernel.core.anyone.service.IAnyoneService;
import com.manage.kernel.core.model.parser.SuperstarParser;
import com.manage.kernel.core.model.parser.WatchParser;
import com.manage.kernel.core.model.vo.HomeVo;
import com.manage.kernel.core.model.vo.NewsDetailVo;
import com.manage.kernel.core.model.vo.NewsVo;
import com.manage.kernel.jpa.entity.JzSuperStar;
import com.manage.kernel.jpa.entity.JzWatch;
import com.manage.kernel.jpa.entity.News;
import com.manage.kernel.jpa.repository.JzSuperStarRepo;
import com.manage.kernel.jpa.repository.JzWatchRepo;
import com.manage.kernel.jpa.repository.NewsRepo;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/10/27.
 */
@Service
public class AnyoneService implements IAnyoneService {


    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private JzWatchRepo watchRepo;

    @Autowired
    private JzSuperStarRepo superStarRepo;

    @Override
    @Transactional
    public HomeVo homeDetail() {

        HomeVo home = new HomeVo();

        LocalDate today = LocalDate.now();
        // 获取今日值班信息
        JzWatch watch = watchRepo.findByWatchTime(today);
        if (watch != null) {
            home.setWatch(WatchParser.toDto(watch));
        }

        // 获取本月星级民警
        String year = String.valueOf(today.getYear());
        String month = String.valueOf(today.getMonthOfYear());
        List<JzSuperStar> superStars = superStarRepo.findByYearMonth(year, month);
        if (!CollectionUtils.isEmpty(superStars)) {
            home.setSuperstar(SuperstarParser.toDto(superStars.get(0)));
        }

        // 获取图片新闻
        home.setPicNews(findPublishNews(NewsType.TPXW, 6));
        int newsCount = 8;
        home.setJqkxNews(findPublishNews(NewsType.JQKX, newsCount));
        home.setDwjsNews(findPublishNews(NewsType.DWJS, newsCount));
        home.setBmdtNews(findPublishNews(NewsType.BMDT, newsCount));
        home.setXxydNews(findPublishNews(NewsType.XXYD, newsCount));
        home.setWhsbNews(findPublishNews(NewsType.WHSB, newsCount));
        home.setKjlwNews(findPublishNews(NewsType.KJLW, newsCount));
        return home;
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
        }
        return detail;
    }

    @Override
    @Transactional
    public List<NewsVo> newsList(NewsType type) {
        List<NewsVo> newsVos = new ArrayList<>();
        if (type == null) {
            return newsVos;
        }

        return findPublishNews(type, 50);
    }

    private List<NewsVo> findPublishNews(NewsType type, int count) {
        List<NewsVo> newsVos = new ArrayList<>();
        PageQuery page = new PageQuery();
        page.setPageNumber(1);
        page.setPageSize(count);
        page.setSortField("publishTime");
        page.setSortDirection(PageQuery.ORDER_DESC);
        Page<News> pageResult = newsRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("type"), type));
            list.add(cb.equal(root.get("status"), NewsStatus.PASS));
            list.add(cb.isNotNull(root.get("publishTime")));
            return cb.and(list.toArray(new Predicate[0]));
        }, page.sortPage());

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
        return newsVos;
    }
}
