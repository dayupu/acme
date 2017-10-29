package com.manage.kernel.core.anyone.service;

import com.manage.base.database.enums.NewsType;
import com.manage.kernel.core.model.vo.HomeVo;
import com.manage.kernel.core.model.vo.NewsDetailVo;
import com.manage.kernel.core.model.vo.NewsVo;

import java.util.List;

/**
 * Created by bert on 2017/10/27.
 */
public interface IAnyoneService {
    public HomeVo homeDetail();

    public NewsDetailVo newsDetail(String number);

    public List<NewsVo> newsList(NewsType type);
}
