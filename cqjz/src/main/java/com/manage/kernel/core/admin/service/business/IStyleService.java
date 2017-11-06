package com.manage.kernel.core.admin.service.business;

import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.kernel.core.model.dto.StyleDto;

/**
 * Created by bert on 2017/11/5.
 */
public interface IStyleService {

    public PageResult pageList(PageQuery page, StyleDto query);

    public StyleDto saveStyle(StyleDto styleDto);

    public StyleDto styleDetail(String number);

    public void dropStyle(String number);
}
