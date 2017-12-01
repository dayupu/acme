package com.manage.kernel.core.anyone.service;

import com.manage.kernel.core.model.dto.NewsTopicDto;
import com.manage.kernel.core.model.vo.TopicHomeVo;

/**
 * Created by bert on 17-12-1.
 */
public interface ITopicService {

    public NewsTopicDto getNewsTopic(Integer code);

    public TopicHomeVo topicHome(Integer code);
}
