package com.manage.kernel.core.admin.service.business;

import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.kernel.core.admin.apply.dto.NewsDto;

/**
 * Created by bert on 17-8-25.
 */
public interface INewsService {

    public NewsDto saveNews(NewsDto newsDto);

    public PageResult pageList(PageQuery page, NewsDto query);

    public NewsDto detail(String number);
}
