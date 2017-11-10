package com.manage.kernel.core.model.parser;

import com.manage.kernel.core.model.dto.NewsTopicDto;
import com.manage.kernel.jpa.entity.NewsTopic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class NewsTopicParser {

    public static NewsTopicDto toDto(NewsTopic newsTopic) {
        return toDto(newsTopic, new NewsTopicDto());
    }

    public static List<NewsTopicDto> toDtoList(List<NewsTopic> entitys) {
        List<NewsTopicDto> dtos = new ArrayList<>();
        for (NewsTopic entity : entitys) {
            dtos.add(toDto(entity, new NewsTopicDto()));
        }
        return dtos;
    }

    private static NewsTopicDto toDto(NewsTopic entity, NewsTopicDto dto) {
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setHasImage(entity.getHasImage());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedUserName());
        dto.setUpdatedBy(entity.getUpdatedUserName());

        List<NewsTopic> topicLines = entity.getTopicLines();
        if (!CollectionUtils.isEmpty(topicLines)) {
            dto.setItemCount(topicLines.size());
            NewsTopicDto line;
            List<NewsTopicDto> lines = new ArrayList<>();
            for (NewsTopic topic : topicLines) {
                line = new NewsTopicDto();
                line.setCode(topic.getCode());
                line.setName(topic.getName());
                line.setHasImage(topic.getHasImage());
                line.setStatus(topic.getStatus());
                line.setDescription(topic.getDescription());
                lines.add(line);
            }
            dto.setTopicLines(lines);
        }
        return dto;
    }
}
