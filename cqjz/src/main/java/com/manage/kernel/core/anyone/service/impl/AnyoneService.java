package com.manage.kernel.core.anyone.service.impl;

import com.manage.base.constant.Image;
import com.manage.base.database.enums.NewsStatus;
import com.manage.base.database.enums.NewsType;
import com.manage.base.database.enums.Status;
import com.manage.base.supplier.Pair;
import com.manage.base.supplier.bootstrap.PageQueryBS;
import com.manage.base.supplier.bootstrap.PageResultBS;
import com.manage.base.utils.FileUtil;
import com.manage.base.utils.StringHandler;
import com.manage.kernel.core.anyone.service.IAnyoneService;
import com.manage.kernel.core.model.dto.StyleDto;
import com.manage.kernel.core.model.dto.SuperstarDto;
import com.manage.kernel.core.model.parser.StyleParser;
import com.manage.kernel.core.model.parser.SuperstarParser;
import com.manage.kernel.core.model.parser.WatchParser;
import com.manage.kernel.core.model.vo.HomeVo;
import com.manage.kernel.core.model.vo.NewsDetailVo;
import com.manage.kernel.core.model.vo.NewsVo;
import com.manage.kernel.core.model.vo.StyleVo;
import com.manage.kernel.jpa.entity.JzStyle;
import com.manage.kernel.jpa.entity.JzStyleLine;
import com.manage.kernel.jpa.entity.JzSuperStar;
import com.manage.kernel.jpa.entity.JzWatch;
import com.manage.kernel.jpa.entity.News;
import com.manage.kernel.jpa.repository.JzStyleRepo;
import com.manage.kernel.jpa.repository.JzSuperStarRepo;
import com.manage.kernel.jpa.repository.JzWatchRepo;
import com.manage.kernel.jpa.repository.NewsRepo;
import org.apache.commons.lang3.StringEscapeUtils;
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

    @Autowired
    private JzStyleRepo styleRepo;

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
        home.setPicNews(findPublishNews(NewsType.TPXW, newsFirstPageRequest(6)).getLeft());
        int newsCount = 8;
        home.setJqkxNews(findPublishNews(NewsType.JQKX, newsFirstPageRequest(newsCount)).getLeft());
        home.setDwjsNews(findPublishNews(NewsType.DWJS, newsFirstPageRequest(newsCount)).getLeft());
        home.setBmdtNews(findPublishNews(NewsType.BMDT, newsFirstPageRequest(newsCount)).getLeft());
        home.setXxydNews(findPublishNews(NewsType.XXYD, newsFirstPageRequest(newsCount)).getLeft());
        home.setWhsbNews(findPublishNews(NewsType.WHSB, newsFirstPageRequest(newsCount)).getLeft());
        home.setKjlwNews(findPublishNews(NewsType.KJLW, newsFirstPageRequest(newsCount)).getLeft());
        home.setJzStyles(findNewestStyles(5));
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
    public StyleDto styleDetail(String number) {
        JzStyle jzStyle = styleRepo.findOne(number);
        return StyleParser.toDto(jzStyle);
    }

    @Override
    public PageResultBS<SuperstarDto> superstarList(PageQueryBS pageQuery) {

        PageRequest pageRequest;
        if ("year".equals(pageQuery.getSortName())) {
            pageRequest = pageQuery.buildPageRequest("year", "month");
        } else {
            pageRequest = pageQuery.buildPageRequest(true);
        }

        PageResultBS<SuperstarDto> result = new PageResultBS<>();
        Page<JzSuperStar> pageResult = superStarRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            return cb.and(list.toArray(new Predicate[0]));
        }, pageRequest);

        SuperstarDto superstarDto;
        List<SuperstarDto> superstarDtos = new ArrayList<>();
        for (JzSuperStar superStar : pageResult.getContent()) {
            superstarDto = new SuperstarDto();
            superstarDto = SuperstarParser.toDto(superStar);
            if (superStar.getPhoto() != null) {
                superstarDto.setImageBase64(FileUtil.imageByteToBase64(superStar.getPhoto(), superStar.getSuffix()));
            } else {
                superstarDto.setImageBase64(Image.DEFAULT_HEAD_IMAGE);
            }
            superstarDtos.add(superstarDto);
        }
        result.setTotal(pageResult.getTotalElements());
        result.setRows(superstarDtos);
        return result;
    }

    @Override
    @Transactional
    public PageResultBS<NewsVo> newsList(NewsType type, PageQueryBS pageQuery) {
        if (type == null) {
            return null;
        }
        Pair<List<NewsVo>, Long> result = findPublishNews(type, pageQuery.buildPageRequest(true));
        PageResultBS<NewsVo> pageResult = new PageResultBS<>();
        pageResult.setTotal(result.getRight());
        pageResult.setRows(result.getLeft());
        for (NewsVo newsVo : pageResult.getRows()) {
            newsVo.setTitle(StringEscapeUtils.escapeHtml4(newsVo.getTitle()));
        }
        return pageResult;
    }

    private PageRequest newsFirstPageRequest(int count) {
        return new PageRequest(0, count, Sort.Direction.DESC, "publishTime");
    }

    @Override
    public PageResultBS<NewsVo> searchNews(PageQueryBS pageQuery) {
        Pair<List<NewsVo>, Long> result = findPublishNews(pageQuery.getSearchText(), pageQuery.buildPageRequest(true));
        PageResultBS<NewsVo> pageResult = new PageResultBS<>();
        pageResult.setTotal(result.getRight());
        pageResult.setRows(result.getLeft());
        for (NewsVo newsVo : pageResult.getRows()) {
            newsVo.setTitle(StringEscapeUtils.escapeHtml4(newsVo.getTitle()));
        }
        return pageResult;
    }

    private Pair<List<NewsVo>, Long> findPublishNews(String searchText, PageRequest pageRequest) {
        return findPublishNews(null, pageRequest, searchText);
    }

    private Pair<List<NewsVo>, Long> findPublishNews(NewsType type, PageRequest pageRequest) {
        return findPublishNews(type, pageRequest, null);
    }

    private Pair<List<NewsVo>, Long> findPublishNews(NewsType type, PageRequest pageRequest, String searchText) {

        List<NewsVo> newsVos = new ArrayList<>();

        Page<News> pageResult = newsRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (type != null) {
                list.add(cb.equal(root.get("type"), type.getConstant()));
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

    private List<StyleVo> findNewestStyles(int count) {

        PageRequest page = new PageRequest(0, count, Sort.Direction.DESC, "createdAt");
        Page<JzStyle> stylePage = styleRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(root.get("status").in(Status.enableList()));
            return cb.and(list.toArray(new Predicate[0]));
        }, page);

        List<StyleVo> styleVos = new ArrayList<>();
        StyleVo styleVo;
        for (JzStyle style : stylePage.getContent()) {
            for (JzStyleLine styleLine : style.getStyleLines()) {
                styleVo = new StyleVo();
                styleVo.setImageId(styleLine.getImageId());
                styleVo.setNumber(styleLine.getNumber());
                styleVos.add(styleVo);
            }
        }

        return styleVos;
    }
}

