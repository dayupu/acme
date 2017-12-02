package com.manage.kernel.core.anyone.service;

import com.manage.base.database.enums.NewsType;
import com.manage.base.supplier.bootstrap.PageQueryBS;
import com.manage.base.supplier.bootstrap.PageResultBS;
import com.manage.kernel.core.model.dto.NewsTopicDto;
import com.manage.kernel.core.model.vo.NewsVo;
import com.manage.kernel.core.model.vo.TopicHomeVo;

/**
 * Created by bert on 17-12-1.
 */
public interface ITopicService {

    public NewsTopicDto getNewsTopic(Integer code);

    public TopicHomeVo topicHome(Integer code);

    public PageResultBS<NewsVo> newsList(Integer type, PageQueryBS pageQuery);
}
