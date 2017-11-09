package com.manage.base.act.vars;

import com.manage.base.act.enums.ActVariable;
import com.manage.base.database.enums.ActProcess;
import com.manage.base.supplier.Pair;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bert on 17-10-19.
 */
public class ActParams {

    private Pair<String, Object> flowApplyUser = new Pair<>(ActVariable.FLOW_APPLY_USER.varName(), null);
    private Pair<String, Object> flowAction = new Pair<>(ActVariable.FLOW_ACTION.varName(), null);
    private Pair<String, Object> flowSubject = new Pair<>(ActVariable.FLOW_SUBJECT.varName(), null);
    private Pair<String, Object> flowBusiness = new Pair<>(ActVariable.FLOW_BUSINESS.varName(), null);
    private Pair<String, Object> taskApproveObj = new Pair<>(ActVariable.TASK_APPROVE.varName(), null);

    public static ActParams flowStart(String applyUser, String subject, String business) {
        ActParams actParams = new ActParams();
        actParams.putFlowApplyUser(applyUser);
        actParams.putFlowSubject(subject);
        actParams.putFlowBusiness(business);
        return actParams;
    }

    public static ActParams flowProcess(ActProcess process, String subject, String business) {
        ActParams actParams = new ActParams();
        actParams.putFlowAction(process.action());
        actParams.putFlowSubject(subject);
        actParams.putFlowBusiness(business);
        return actParams;
    }

    public String varFlowApplyUser() {
        return flowApplyUser.key();
    }

    public String varFlowAction() {
        return flowAction.key();
    }

    public String varFlowSubject() {
        return flowSubject.key();
    }

    public String varFlowBusiness() {
        return flowBusiness.key();
    }

    public String varTaskAPproveObj() {
        return taskApproveObj.key();
    }

    public void putFlowApplyUser(Object value) {
        this.flowApplyUser.setRight(value);
    }

    public void putFlowAction(Object value) {
        this.flowAction.setRight(value);
    }

    public void putFlowSubject(Object value) {
        this.flowSubject.setRight(value);
    }

    public void putFlowBusiness(Object value) {
        this.flowBusiness.setRight(value);
    }

    public void putTaskApproveObj(Object value) {
        this.taskApproveObj.setRight(value);
    }

    public Map<String, Object> build() {
        Map<String, Object> param = new HashMap();
        if (flowApplyUser.value() != null)
            param.put(flowApplyUser.key(), flowApplyUser.value());
        if (flowAction.value() != null)
            param.put(flowAction.key(), flowAction.value());
        if (flowSubject.value() != null)
            param.put(flowSubject.key(), flowSubject.value());
        if (flowBusiness.value() != null)
            param.put(flowBusiness.key(), flowBusiness.value());
        if (taskApproveObj.value() != null)
            param.put(taskApproveObj.key(), taskApproveObj.value());
        return param;
    }
}
