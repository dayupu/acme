package com.manage.kernel.core.anyone.service;

import com.manage.kernel.core.model.dto.NewsTopicDto;

/**
 * Created by bert on 17-12-1.
 */
public interface ITopicService {

    public NewsTopicDto getNewsTopic(String code);
}
