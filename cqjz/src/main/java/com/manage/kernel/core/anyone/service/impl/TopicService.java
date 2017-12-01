package com.manage.kernel.core.anyone.service.impl;

import com.manage.base.exception.NewsTopicNotFoundException;
import com.manage.kernel.core.anyone.service.ITopicService;
import com.manage.kernel.core.model.dto.NewsTopicDto;
import com.manage.kernel.core.model.parser.NewsTopicParser;
import com.manage.kernel.jpa.entity.NewsTopic;
import com.manage.kernel.jpa.repository.NewsTopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bert on 17-12-1.
 */
@Service
public class TopicService implements ITopicService {

    @Autowired
    private NewsTopicRepo topicRepo;

    @Override
    @Transactional
    public NewsTopicDto getNewsTopic(String code) {

        NewsTopic topic = topicRepo.findOne(Long.valueOf(code));
        if (topic == null || topic.getLevel() != 1) {
            throw new NewsTopicNotFoundException();
        }

        return NewsTopicParser.toDto(topic);
    }
}
