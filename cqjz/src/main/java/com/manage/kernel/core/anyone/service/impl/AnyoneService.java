package com.manage.kernel.core.anyone.service.impl;

import com.manage.base.database.enums.NewsStatus;
import com.manage.base.database.enums.NewsType;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.utils.FileUtil;
import com.manage.kernel.core.anyone.service.IAnyoneService;
import com.manage.kernel.core.model.dto.SuperstarDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

        // 获取最新季度的星级民警
        home.setSuperstars(getJzSuperStars());
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

    private List<SuperstarDto> getJzSuperStars() {
        Page<JzSuperStar> pageResult = superStarRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            return cb.and(list.toArray(new Predicate[0]));
        }, new PageRequest(0, 1, Sort.Direction.DESC, "year", "month"));

        if (CollectionUtils.isEmpty(pageResult.getContent())) {
            return new ArrayList<>();
        }

        JzSuperStar superStar = pageResult.getContent().get(0);
        String year = superStar.getYear();
        String month = superStar.getMonth();
        List<String> months = new ArrayList<>();
        switch (Integer.valueOf(month)) {
            case 1:
            case 2:
            case 3:
                months.add("1");
                months.add("2");
                months.add("3");
                break;
            case 4:
            case 5:
            case 6:
                months.add("4");
                months.add("5");
                months.add("6");
                break;
            case 7:
            case 8:
            case 9:
                months.add("7");
                months.add("8");
                months.add("9");
                break;
            case 10:
            case 11:
            case 12:
                months.add("10");
                months.add("11");
                months.add("12");
                break;
        }

        SuperstarDto superstarDto;
        List<SuperstarDto> superstarDtos = new ArrayList<>();
        for (JzSuperStar entity : superStarRepo.findByYearMonth(year, months)) {
            superstarDto = SuperstarParser.toDto(entity);
            if (superStar.getPhoto() != null) {
                superstarDto.setImageBase64(FileUtil.imageByteToBase64(superStar.getPhoto(), superStar.getSuffix()));
            }
            superstarDtos.add(superstarDto);
        }

        return superstarDtos;
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

        Page<News> pageResult = newsRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("type"), type));
            list.add(cb.equal(root.get("status"), NewsStatus.PASS));
            list.add(cb.isNotNull(root.get("publishTime")));
            return cb.and(list.toArray(new Predicate[0]));
        }, new PageRequest(0, count, Sort.Direction.DESC, "publishTime"));

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
