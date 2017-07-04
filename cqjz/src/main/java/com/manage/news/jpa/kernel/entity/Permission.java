package com.manage.news.jpa.kernel.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @Column(name = "code", length = 60)
    private String code;

    @Column(name = "group_code", length = 60)
    private String groupCode;

    @Column(name = "resouce_key", length = 200)
    private String resouceKey;

    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn = new Date();

    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<Role>();

    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<User>();

    public Permission() {

    }

    public Permission(String code, String groupCode, String resouceKey, String type) {
        this.code = code;
        this.groupCode = groupCode;
        this.resouceKey = resouceKey;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getResouceKey() {
        return resouceKey;
    }

    public void setResouceKey(String resouceKey) {
        this.resouceKey = resouceKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
