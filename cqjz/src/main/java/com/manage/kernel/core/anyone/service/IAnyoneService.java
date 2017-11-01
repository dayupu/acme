package com.manage.kernel.core.anyone.service;

import com.manage.base.database.enums.NewsType;
import com.manage.base.supplier.bootstrap.PageQueryBS;
import com.manage.base.supplier.bootstrap.PageResultBS;
import com.manage.kernel.core.model.vo.HomeVo;
import com.manage.kernel.core.model.vo.NewsDetailVo;
import com.manage.kernel.core.model.vo.NewsVo;

/**
 * Created by bert on 2017/10/27.
 */
public interface IAnyoneService {
    public HomeVo homeDetail();

    public NewsDetailVo newsDetail(String number);

    public PageResultBS<NewsVo> newsList(NewsType type, PageQueryBS pageQuery);
}
