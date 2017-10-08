package com.manage.kernel.core.admin.service.activiti;

import com.manage.base.act.ActBusiness;
import com.manage.base.act.ActSource;
import com.manage.base.enums.ActProcess;
import com.manage.kernel.core.admin.apply.dto.NewsDto;
import com.manage.kernel.jpa.news.entity.User;

/**
 * Created by bert on 2017/10/3.
 */
public interface IActBusinessService {

    public void changeStatus(ActBusiness actBusiness, ActProcess process, boolean processEnd);
    public void submit(ActBusiness actBusiness);
}
