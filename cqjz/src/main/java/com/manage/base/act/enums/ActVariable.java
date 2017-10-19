package com.manage.base.act.enums;

/**
 * Created by bert on 17-10-17.
 */
public enum ActVariable {

    /*流程变量*/
    FLOW_ACTION("action"),
    FLOW_APPLY_USER("applyUser"),
    FLOW_SUBJECT("flow_subject"),
    FLOW_BUSINESS_TYPE("flow_businessType"),
    /*任务变量*/
    TASK_APPROVE("task_approve");

    private String variableName;

    ActVariable(String variableName) {
        this.variableName = variableName;
    }

    public static ActVariable fromVarName(String variableName) {
        if (variableName != null) {
            for (ActVariable variable : ActVariable.values()) {
                if (variable.varName().equals(variableName)) {
                    return variable;
                }
            }
        }
        return null;
    }

    public String varName() {
        return variableName;
    }

}
