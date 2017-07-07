package com.manage.cache.bean;

import java.io.Serializable;

public class TokenUser implements Serializable{

    private String account;
    private String[] roles;
    private String ip;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
