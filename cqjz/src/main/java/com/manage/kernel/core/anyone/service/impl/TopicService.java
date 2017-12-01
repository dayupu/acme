package com.manage.kernel.core.anyone.service.impl;

import com.manage.base.exception.NewsTopicNotFoundException;
import com.manage.kernel.core.anyone.service.IAnyNewsService;
import com.manage.kernel.core.anyone.service.ITopicService;
import com.manage.kernel.core.model.dto.NewsTopicDto;
import com.manage.kernel.core.model.parser.NewsTopicParser;
import com.manage.kernel.core.model.vo.TopicHomeVo;
import com.manage.kernel.core.model.vo.TopicVo;
import com.manage.kernel.jpa.entity.NewsTopic;
import com.manage.kernel.jpa.repository.NewsTopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bert on 17-12-1.
 */
@Service
public class TopicService implements ITopicService {

    @Autowired
    private NewsTopicRepo topicRepo;

    @Autowired
    private IAnyNewsService newsService;

    @Override
    @Transactional
    public NewsTopicDto getNewsTopic(Integer code) {
        NewsTopic topic = getSuperTopic(code);
        return NewsTopicParser.toDto(topic);
    }

    @Override
    @Transactional
    public TopicHomeVo topicHome(Integer code) {
        NewsTopic topic = getSuperTopic(code);
        TopicHomeVo homeVo = new TopicHomeVo();
        homeVo.setCode(topic.getCode());
        homeVo.setName(topic.getName());
        homeVo.setImageId(topic.getImageId());
        for (NewsTopic line : topic.getTopicLines()) {
            if (line.getHasImage()) {
                homeVo.getImgColumns().add(topicColumnDetail(line, 6));
            } else {
                homeVo.getColumns().add(topicColumnDetail(line, 8));
            }
        }
        return homeVo;
    }

    private TopicVo topicColumnDetail(NewsTopic topic, int count) {
        TopicVo vo = new TopicVo();
        vo.setName(topic.getName());
        vo.setCode(topic.getCode());
        vo.setNewses(newsService.findPublishNews(topic.getCode(), onePageRequest(count)).getLeft());
        return vo;
    }

    private NewsTopic getSuperTopic(Integer code) {
        NewsTopic topic = topicRepo.findOne(code);
        if (topic == null || topic.getLevel() != 1) {
            throw new NewsTopicNotFoundException();
        }
        return topic;
    }

    private PageRequest onePageRequest(int count) {
        return new PageRequest(0, count, Sort.Direction.DESC, "publishTime");
    }
}
