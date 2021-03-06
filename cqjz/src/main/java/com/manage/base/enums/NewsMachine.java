package com.manage.base.enums;

import com.manage.base.constant.Constants;
import com.manage.base.database.enums.ActProcess;
import com.manage.base.database.enums.ApproveRole;
import com.manage.base.exception.ActNotSupportException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

/**
 * Created by bert on 2017/11/12.
 */
public enum NewsMachine {

    APPLY_TASK("step01-01", ApproveRole.EMPLOYEE),
    REJECT_TASK("step01-02", ApproveRole.EMPLOYEE),
    CAPTAIN_TASK("step02", ApproveRole.CAPTAIN),
    SECURER_TASK("step03", ApproveRole.SECURER),
    LEADER_TASK("step04", ApproveRole.LEADER);

    private String taskKey;
    private ApproveRole role;

    NewsMachine(String taskKey, ApproveRole role) {
        this.taskKey = taskKey;
        this.role = role;
    }

    public static List<String> nextGroupIds(String taskKey, ActProcess process, String organCode) {

        NewsMachine machine = nextMachine(taskKey, process);
        if (machine == null) {
            return null;
        }
        return actGroupIds(machine.role, organCode);
    }

    private static NewsMachine fromTaskId(String taskKey) {
        for (NewsMachine machine : NewsMachine.values()) {
            if (machine.taskKey.equals(taskKey)) {
                return machine;
            }
        }
        throw new ActNotSupportException();
    }

    private static NewsMachine nextMachine(String taskId, ActProcess process) {
        NewsMachine machine = fromTaskId(taskId);
        if (machine == null) {
            throw new ActNotSupportException();
        }
        if (process.isReject()) {
            return null;
        }
        NewsMachine nextMachine;
        switch (machine) {
        case APPLY_TASK:
        case REJECT_TASK:
            nextMachine = CAPTAIN_TASK;
            break;
        case CAPTAIN_TASK:
            nextMachine = SECURER_TASK;
            break;
        case SECURER_TASK:
            nextMachine = LEADER_TASK;
            break;
        case LEADER_TASK:
            nextMachine = null;
            break;
        default:
            throw new ActNotSupportException();
        }
        return nextMachine;
    }

    private static List<String> actGroupIds(ApproveRole role, String organCode) {
        List<String> groupIds = new ArrayList<>();
        if (StringUtils.isEmpty(organCode)) {
            return groupIds;
        }
        String code = organCode;
        do {
            groupIds.add(actGroupId(role, code));
            code = code.substring(0, code.length() - Constants.ORGAN_LENTH);
        } while (!StringUtils.isEmpty(code));
        return groupIds;
    }

    public static String actGroupId(ApproveRole role, String organCode) {
        return role.getCode() + "_" + organCode;
    }
}
