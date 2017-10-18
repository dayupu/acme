package com.manage.kernel.core.admin.service.business;

import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.kernel.core.admin.apply.dto.*;
import com.manage.kernel.jpa.entity.AdUser;

/**
 * Created by bert on 17-8-25.
 */
public interface IFlowService {
    public PageResult submitTaskList(PageQuery page, FlowDto query);

    public PageResult pendingTaskList(PageQuery page, FlowDto query);

    public PageResult rejectTaskList(PageQuery page, FlowDto query);

    public PageResult approvedTaskList(PageQuery page, FlowDto query);

    public ProcessDetail approveTask(ApproveDto approveDto);

    public ProcessDetail processDetail(String processId, boolean isApprove);

    public NewestFlowDto newestFlow(AdUser user);

    public FlowNotification notification(AdUser user);

    public void cancelProcess(String processId);
}
