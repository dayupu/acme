package com.manage.kernel.core.admin.parser;

import com.manage.kernel.core.admin.dto.NewsDto;
import com.manage.kernel.jpa.news.entity.News;

import java.util.ArrayList;
import java.util.List;

public class NewsParser {

    public static NewsDto toNewsDto(News news) {
        return toNewsDto(news, new NewsDto());
    }

    public static List<NewsDto> toNewsDtoList(List<News> newss) {
        List<NewsDto> newsDtos = new ArrayList<>();
        for (News news : newss) {
            newsDtos.add(toNewsDto(news, new NewsDto()));
        }
        return newsDtos;
    }

    private static NewsDto toNewsDto(News news, NewsDto newsDto) {
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setContent(newsDto.getContent());
        newsDto.setSource(news.getSource());
        newsDto.setType(news.getType());
        return newsDto;
    }
}
