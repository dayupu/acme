package com.manage.kernel.core.admin.service.business;

import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.kernel.core.admin.apply.dto.ApproveDto;
import com.manage.kernel.core.admin.apply.dto.ApproveHistory;
import com.manage.kernel.core.admin.apply.dto.FlowDto;
import com.manage.kernel.core.admin.apply.dto.ProcessDetail;

import java.util.List;

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

}
