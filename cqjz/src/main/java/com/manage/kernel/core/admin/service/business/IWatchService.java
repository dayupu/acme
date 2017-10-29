package com.manage.kernel.core.admin.service.business;

import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.kernel.core.model.dto.WatchDto;
import org.joda.time.LocalDate;

/**
 * Created by bert on 17-8-25.
 */
public interface IWatchService {

    public PageResult pageList(PageQuery page, WatchDto query);

    public void saveWatch(WatchDto watchDto);

    public WatchDto detail(LocalDate watchTime);

    public void drop(Long id);

}
