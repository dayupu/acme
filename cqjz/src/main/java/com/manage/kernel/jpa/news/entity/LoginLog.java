package com.manage.kernel.jpa.news.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ad_login_log")
@SequenceGenerator(name = "seq_login_log", sequenceName = "seq_login_log")
public class LoginLog {

    @Id
    @GeneratedValue(generator = "seq_login_log", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "account")
    private String account;

    @Column(name = "client_ip")
    private String clientIP;

    @Column(name = "message", length = 100)
    private String message;

    @Column(name = "success")
    private Boolean success;

    @Column(name = "login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
