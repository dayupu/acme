package com.manage.kernel.core.anyone.service;

import com.manage.base.supplier.Pair;
import com.manage.kernel.core.model.vo.NewsDetailVo;
import com.manage.kernel.core.model.vo.NewsVo;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 * Created by bert on 17-12-1.
 */
public interface IAnyNewsService {

    public Pair<List<NewsVo>, Long> findPublishNews(Integer type, PageRequest pageRequest);
    public Pair<List<NewsVo>, Long> findPublishNews(Integer type, PageRequest pageRequest, String searchText);
    public NewsDetailVo newsDetail(String number);
}
