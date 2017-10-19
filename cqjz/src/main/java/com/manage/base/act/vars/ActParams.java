package com.manage.base.act.vars;

import com.manage.base.act.enums.ActVariable;
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
    private Pair<String, Object> flowBusinessType = new Pair<>(ActVariable.FLOW_BUSINESS_TYPE.varName(), null);
    private Pair<String, Object> taskApproveObj = new Pair<>(ActVariable.TASK_APPROVE.varName(), null);

    public String varFlowApplyUser() {
        return flowApplyUser.key();
    }

    public String varFlowAction() {
        return flowAction.key();
    }

    public String varFlowSubject() {
        return flowSubject.key();
    }

    public String varFlowBusinessType() {
        return flowBusinessType.key();
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

    public void putFlowBusinessType(Object value) {
        this.flowBusinessType.setRight(value);
    }

    public void putTaskApproveObj(Object value) {
        this.taskApproveObj.setRight(value);
    }

    public Map<String, Object> buildMap() {
        Map<String, Object> param = new HashMap();
        if (flowApplyUser.value() != null)
            param.put(flowApplyUser.key(), flowApplyUser.value());
        if (flowAction.value() != null)
            param.put(flowAction.key(), flowAction.value());
        if (flowSubject.value() != null)
            param.put(flowSubject.key(), flowSubject.value());
        if (flowBusinessType.value() != null)
            param.put(flowBusinessType.key(), flowBusinessType.value());
        if (taskApproveObj.value() != null)
            param.put(taskApproveObj.key(), taskApproveObj.value());
        return param;
    }
}
