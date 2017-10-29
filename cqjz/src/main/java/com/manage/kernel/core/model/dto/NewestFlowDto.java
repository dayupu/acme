package com.manage.kernel.core.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-10-18.
 */
public class NewestFlowDto {

    private List<FlowDto> pendingTask = new ArrayList<>();

    private List<FlowDto> rejectTask = new ArrayList<>();

    public List<FlowDto> getPendingTask() {
        return pendingTask;
    }

    public void setPendingTask(List<FlowDto> pendingTask) {
        this.pendingTask = pendingTask;
    }

    public List<FlowDto> getRejectTask() {
        return rejectTask;
    }

    public void setRejectTask(List<FlowDto> rejectTask) {
        this.rejectTask = rejectTask;
    }
}
