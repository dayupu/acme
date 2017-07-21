package com.manage.news.jpa.kernel.entity;

import com.manage.base.converter.StatusAttributeConverter;
import com.manage.base.enums.Status;
import com.manage.news.jpa.kernel.base.CommonBase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "admin_user")
@SequenceGenerator(name = "seq_admin_user", sequenceName = "seq_admin_user")
public class User extends CommonBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_admin_user")
    private Long id;

    @Column(name = "account", length = 50)
    private String account;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "telephone", length = 50)
    private String telephone;

    @Column(name = "mobile", length = 50)
    private String mobile;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "status")
    @Convert(converter = StatusAttributeConverter.class)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", referencedColumnName = "id")
    private Department department;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "admin_user_permission", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "permission_code"))
    private List<Permission> permissions = new ArrayList<Permission>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "admin_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<Role>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
