package com.manage.kernel.core.admin.service.business;

import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.kernel.core.model.dto.NewsDto;
import com.manage.kernel.jpa.entity.AdUser;

import java.util.List;

/**
 * Created by bert on 17-8-25.
 */
public interface INewsService {

    public NewsDto saveNews(NewsDto newsDto);

    public NewsDto submitNews(NewsDto newsDto);

    public PageResult pageList(PageQuery page, NewsDto query);

    public NewsDto detail(String number);

    public void drop(String number);

    public List<NewsDto> newestNews(AdUser adUser);

}
