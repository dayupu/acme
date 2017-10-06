package com.manage.kernel.core.admin.service.activiti;

import com.manage.kernel.core.admin.apply.dto.NewsDto;
import com.manage.kernel.jpa.news.entity.User;

/**
 * Created by bert on 2017/10/3.
 */
public interface INewsActivitiService {

    public void submit(User user, NewsDto news);
}
