package com.manage.kernel.core.admin.apply.dto;

/**
 * Created by bert on 17-10-18.
 */
public class FlowNotification {

    private long total = 0;
    private long pendingCount = 0;
    private long rejectCount = 0;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(long pendingCount) {
        this.pendingCount = pendingCount;
    }

    public long getRejectCount() {
        return rejectCount;
    }

    public void setRejectCount(long rejectCount) {
        this.rejectCount = rejectCount;
    }

    public void calculateTotal() {
        total = pendingCount + rejectCount;
    }
}
