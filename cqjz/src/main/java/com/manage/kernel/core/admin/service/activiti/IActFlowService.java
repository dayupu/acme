package com.manage.kernel.core.admin.service.activiti;

import com.manage.base.act.support.ActFlowSupport;
import com.manage.base.database.enums.ActProcess;

/**
 * Created by bert on 2017/10/3.
 */
public interface IActFlowService {

    public void actFlowCommit(ActFlowSupport flowSupport);

    public void handleBusinessStatus(String businessId, ActProcess process, boolean isOver);

    public void clearAll();

}
