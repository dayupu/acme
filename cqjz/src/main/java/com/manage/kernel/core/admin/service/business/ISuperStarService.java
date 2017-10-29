package com.manage.kernel.core.admin.service.business;

import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.kernel.core.model.dto.SuperstarDto;

/**
 * Created by bert on 17-8-25.
 */
public interface ISuperStarService {

    public PageResult pageList(PageQuery page, SuperstarDto query);

    public SuperstarDto saveSuperstar(SuperstarDto superstarDto);

    public SuperstarDto detail(Long id);

    public void drop(Long id);

}
