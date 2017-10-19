package com.manage.base.act.vars;

import com.manage.base.database.enums.ActProcess;

import java.io.Serializable;

/**
 * Created by bert on 2017/10/8.
 */
public class ActApproveObj implements Serializable {

    private static final long serialVersionUID = 5605220837822865457L;

    private String userId;
    private ActProcess process;
    private String comment;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ActProcess getProcess() {
        return process;
    }

    public void setProcess(ActProcess process) {
        this.process = process;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
