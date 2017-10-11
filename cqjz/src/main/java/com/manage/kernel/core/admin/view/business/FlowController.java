package com.manage.kernel.core.admin.view.business;

import com.manage.base.exception.CoreException;
import com.manage.base.exception.ValidateException;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.supplier.page.ResponseInfo;
import com.manage.base.utils.Validators;
import com.manage.kernel.core.admin.apply.dto.ApproveDto;
import com.manage.kernel.core.admin.apply.dto.FlowDto;
import com.manage.kernel.core.admin.apply.dto.NewsDto;
import com.manage.kernel.core.admin.service.business.IFlowService;
import com.manage.kernel.core.admin.view.base.DepartController;
import com.manage.kernel.spring.annotation.InboundLog;
import com.manage.kernel.spring.annotation.PageQueryAon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bert on 2017/10/6.
 */
@RestController
@RequestMapping("/admin/flow")
public class FlowController {

    private static final Logger LOGGER = LogManager.getLogger(FlowController.class);
    @Autowired
    private IFlowService flowService;

    @InboundLog
    @PostMapping("/list/submit")
    public ResponseInfo pageListForSubmit(@PageQueryAon PageQuery page, @RequestBody FlowDto query) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = flowService.submitTaskList(page, query);
        response.wrapSuccess(result);
        return response;
    }

    @InboundLog
    @PostMapping("/list/pending")
    public ResponseInfo pageListForPending(@PageQueryAon PageQuery page, @RequestBody FlowDto query) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = flowService.pendingTaskList(page, query);
        response.wrapSuccess(result);
        return response;
    }

    @InboundLog
    @PostMapping("/list/reject")
    public ResponseInfo pageListForReject(@PageQueryAon PageQuery page, @RequestBody FlowDto query) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = flowService.rejectTaskList(page, query);
        response.wrapSuccess(result);
        return response;
    }

    @InboundLog
    @PostMapping("/list/approve")
    public ResponseInfo pageListForApprove(@PageQueryAon PageQuery page, @RequestBody FlowDto query) {
        ResponseInfo response = new ResponseInfo();
        PageResult result = flowService.approvedTaskList(page, query);
        response.wrapSuccess(result);
        return response;
    }

    @InboundLog
    @GetMapping("/{processId}")
    public ResponseInfo processDetail(@PathVariable("processId") String processId) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(processId);
            response.wrapSuccess(flowService.processDetail(processId, false));
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @GetMapping("/{processId}/approve")
    public ResponseInfo processDetailApprove(@PathVariable("processId") String processId) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notNull(processId);
            response.wrapSuccess(flowService.processDetail(processId, true));
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }

    @InboundLog
    @PostMapping("/approve")
    public ResponseInfo taskApprove(@RequestBody ApproveDto approveDto) {
        ResponseInfo response = new ResponseInfo();
        try {
            Validators.notBlank(approveDto.getTaskId());
            Validators.notNull(approveDto.getProcess());
            Validators.notBlank(approveDto.getComment());
            response.wrapSuccess(flowService.approveTask(approveDto));
        } catch (ValidateException e) {
            response.wrapFail(e.getMessage());
        } catch (CoreException e) {
            response.wrapFail(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("system exception", e);
            response.wrapError();
        }
        return response;
    }
}
