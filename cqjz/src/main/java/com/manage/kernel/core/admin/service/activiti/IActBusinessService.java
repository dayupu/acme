package com.manage.kernel.core.admin.service.activiti;

import com.manage.base.act.ActApprove;
import com.manage.base.act.ActBusiness;
import com.manage.base.database.enums.ActProcess;
import org.activiti.engine.task.TaskInfo;

/**
 * Created by bert on 2017/10/3.
 */
public interface IActBusinessService {

    public void saveApproveTask(TaskInfo taskInfo, ActBusiness actBusiness, ActApprove approve);

    public void changeStatus(ActBusiness actBusiness, ActProcess process, boolean processEnd);

    public void submit(ActBusiness actBusiness);

    public String getSubject(String processId);
}
