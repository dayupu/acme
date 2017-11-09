package com.manage.kernel.core.admin.service.activiti;

import com.manage.base.act.vars.ActApproveObj;
import com.manage.base.act.support.ActBusiness;
import com.manage.base.act.ProcessUser;
import com.manage.base.act.ProcessVariable;
import com.manage.base.database.enums.ActProcess;
import org.activiti.engine.task.TaskInfo;

/**
 * Created by bert on 2017/10/3.
 */
public interface IActBusinessService {

    public void saveApproveTask(TaskInfo taskInfo, String businessId, ActApproveObj approve);

    public ProcessVariable getProcessVaribale(String processId);

    public ProcessUser getProcessUser(String actUserId);
}
