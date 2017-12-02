package com.manage.kernel.core.anyone.service.impl;

import com.manage.base.exception.NewsTopicNotFoundException;
import com.manage.base.supplier.Pair;
import com.manage.base.supplier.bootstrap.PageQueryBS;
import com.manage.base.supplier.bootstrap.PageResultBS;
import com.manage.kernel.core.anyone.service.IAnyNewsService;
import com.manage.kernel.core.anyone.service.ITopicService;
import com.manage.kernel.core.model.dto.NewsTopicDto;
import com.manage.kernel.core.model.parser.NewsTopicParser;
import com.manage.kernel.core.model.vo.NewsVo;
import com.manage.kernel.core.model.vo.TopicHomeVo;
import com.manage.kernel.core.model.vo.TopicVo;
import com.manage.kernel.jpa.entity.NewsTopic;
import com.manage.kernel.jpa.repository.NewsTopicRepo;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
            if(!line.getStatus().isEnable()){
                continue;
            }
            if (line.getHasImage()) {
                homeVo.getImgColumns().add(topicColumnDetail(line, 7));
            } else {
                homeVo.getColumns().add(topicColumnDetail(line, 6));
            }
        }
        return homeVo;
    }

    @Override
    @Transactional
    public PageResultBS<NewsVo> newsList(Integer type, PageQueryBS pageQuery) {
        Pair<List<NewsVo>, Long> result = newsService.findPublishNews(type, pageQuery.buildPageRequest(true));
        PageResultBS<NewsVo> pageResult = new PageResultBS<>();
        pageResult.setTotal(result.getRight());
        pageResult.setRows(result.getLeft());
        for (NewsVo newsVo : pageResult.getRows()) {
            newsVo.setTitle(StringEscapeUtils.escapeHtml4(newsVo.getTitle()));
        }
        return pageResult;
    }


    @Override
    @Transactional
    public List<NewsTopicDto> topicTypes() {
        List<NewsTopic> topics = topicRepo.queryAllRootTopics();
        List<NewsTopicDto> topicDtos = new ArrayList<>();
        for (NewsTopic topic : topics) {
            if (!topic.getStatus().isEnable()) {
                continue;
            }

            NewsTopicDto dto = new NewsTopicDto();
            dto.setCode(topic.getCode());
            dto.setName(topic.getName());
            topicDtos.add(dto);
        }
        return topicDtos;
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
