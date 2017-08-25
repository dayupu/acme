package com.manage.kernel.jpa.news.entity;

import com.manage.base.extend.enums.Permit;
import com.manage.base.extend.enums.PermitType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Entity
@Table(name = "ad_permission")
public class Permission {

    @Id
    @Column(length = 20)
    private String code;

    @Column(name = "permit", length = 60, nullable = false)
    @Type(type = "com.manage.base.extend.model.VarDBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.extend.enums.Permit")})
    private Permit permit;

    @Column(name = "parent_code", length = 20)
    private String parentCode;

    @Column(name = "message_key", length = 200)
    private String messageKey;

    @Column(name = "type", length = 20)
    @Type(type = "com.manage.base.extend.model.DBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.extend.enums.PermitType")})
    private PermitType type;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn = new Date();

    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<Role>();

    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<User>();

    public Permission() {

    }

    public Permission(String code) {
        this.code = code;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public PermitType getType() {
        return type;
    }

    public void setType(PermitType type) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Permit getPermit() {
        return permit;
    }

    public void setPermit(Permit permit) {
        this.permit = permit;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
