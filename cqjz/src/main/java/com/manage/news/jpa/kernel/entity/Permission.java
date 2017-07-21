package com.manage.news.jpa.kernel.entity;

import com.manage.base.converter.PermitAttributeConverter;
import com.manage.base.enums.Permit;
import com.manage.base.enums.PermitType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "admin_permission", uniqueConstraints = { @UniqueConstraint(columnNames = { "permit", "permit_group" }) })
@SequenceGenerator(name = "seq_admin_permission", sequenceName = "seq_admin_permission", allocationSize = 1)
public class Permission {

    @Id
    @GeneratedValue(generator = "seq_admin_permission", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "permit", length = 60, nullable = false)
    @Convert(converter = PermitAttributeConverter.class)
    private Permit permit;

    @Column(name = "permit_group", length = 60)
    @Convert(converter = PermitAttributeConverter.class)
    private Permit permitGroup;

    @Column(name = "resource", length = 200)
    private String resource;

    @Column(name = "type", length = 20)
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

    @Transient
    public String permitKey() {
        String permitKey = permit.getCode();
        if (permitGroup != null) {
            permitKey += ":" + permitGroup.getCode();
        }
        return permitKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Permit getPermit() {
        return permit;
    }

    public void setPermit(Permit permit) {
        this.permit = permit;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Permit getPermitGroup() {
        return permitGroup;
    }

    public void setPermitGroup(Permit permitGroup) {
        this.permitGroup = permitGroup;
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
}
