package com.manage.kernel.core.model.parser;

import com.manage.kernel.core.model.dto.NewsAttachDto;
import com.manage.kernel.core.model.dto.NewsDto;
import com.manage.kernel.jpa.entity.News;

import com.manage.kernel.jpa.entity.NewsAttach;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class NewsParser {

    public static NewsDto toDto(News news) {
        return toDto(news, new NewsDto());
    }

    public static List<NewsDto> toDtoList(List<News> newsList) {
        List<NewsDto> newsDtos = new ArrayList<>();
        for (News news : newsList) {
            newsDtos.add(toDto(news, new NewsDto()));
        }
        return newsDtos;
    }

    private static NewsDto toDto(News news, NewsDto newsDto) {
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setNumber(news.getNumber());
        newsDto.setContent(news.getContent());
        newsDto.setType(news.getType());
        newsDto.setSource(news.getSource());
        newsDto.setStatus(news.getStatus());
        newsDto.setImageId(news.getImageId());
        newsDto.setApprovedTime(news.getPublishTime());
        newsDto.setCreatedAt(news.getCreatedAt());
        newsDto.setUpdatedAt(news.getUpdatedAt());
        newsDto.setCreatedBy(news.getCreatedUserName());
        newsDto.setUpdatedBy(news.getUpdatedUserName());
        newsDto.setCanEdit(news.getStatus().canEdit());
        newsDto.setCanDrop(news.getStatus().canDrop());
        if (!CollectionUtils.isEmpty(news.getAttaches())) {
            NewsAttachDto dto;
            for (NewsAttach attach : news.getAttaches()) {
                dto = new NewsAttachDto();
                dto.setFileId(attach.getFileId());
                dto.setFileName(attach.getFileName());
                newsDto.getAttachments().add(dto);
            }
        }
        return newsDto;
    }
}
