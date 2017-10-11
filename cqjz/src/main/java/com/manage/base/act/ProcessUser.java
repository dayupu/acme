package com.manage.base.act;

/**
 * Created by bert on 2017/10/10.
 */
public class ProcessUser {

    private Long userId;
    private String actUserId;
    private String userName;
    private Long userOrganId;
    private String userOrganName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserOrganId() {
        return userOrganId;
    }

    public void setUserOrganId(Long userOrganId) {
        this.userOrganId = userOrganId;
    }

    public String getUserOrganName() {
        return userOrganName;
    }

    public void setUserOrganName(String userOrganName) {
        this.userOrganName = userOrganName;
    }

    public String getActUserId() {
        return actUserId;
    }

    public void setActUserId(String actUserId) {
        this.actUserId = actUserId;
    }
}
