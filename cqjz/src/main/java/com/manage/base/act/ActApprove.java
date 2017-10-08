package com.manage.base.act;

import com.manage.base.enums.ActProcess;

import java.io.Serializable;

/**
 * Created by bert on 2017/10/8.
 */
public class ActApprove implements Serializable {

    private static final long serialVersionUID = 5605220837822865457L;

    private String userId;
    private ActProcess process;

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
}
