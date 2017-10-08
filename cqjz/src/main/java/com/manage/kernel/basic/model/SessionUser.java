package com.manage.kernel.basic.model;

/**
 * Created by bert on 2017/10/6.
 */
public class SessionUser {

    private Long id;
    private String account;

    public SessionUser(Long id, String account) {
        this.id = id;
        this.account = account;
    }

    public Long id() {
        return id;
    }

    public String account() {
        return account;
    }
}
