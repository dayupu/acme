package com.manage.kernel.core.admin.service.activiti.query;

/**
 * Created by bert on 2017/10/6.
 */
public class TaskQuery {

    private String groupId;
    private String userId;
    private String subject;

    public String getGroupId() {
        return groupId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
