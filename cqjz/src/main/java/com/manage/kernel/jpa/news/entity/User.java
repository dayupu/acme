package com.manage.kernel.jpa.news.entity;

import com.manage.base.database.enums.ApproveRole;
import com.manage.base.database.enums.Gender;
import com.manage.kernel.jpa.news.base.StatusBase;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ad_user")
@SequenceGenerator(name = "seq_user", sequenceName = "seq_user")
public class User extends StatusBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    private Long id;

    @Column(name = "account", nullable = false, length = 50)
    private String account;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "gender")
    @Type(type = "com.manage.base.database.model.DBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.database.enums.Gender")})
    private Gender gender;

    @Column(name = "approve_role")
    @Type(type = "com.manage.base.database.model.DBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.database.enums.ApproveRole")})
    private ApproveRole approveRole;

    @Column(name = "telephone", length = 50)
    private String telephone;

    @Column(name = "mobile", length = 50)
    private String mobile;

    @Column(name = "email", length = 50)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ad_user_permission", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "permission_code"))
    private List<Permission> permissions = new ArrayList<Permission>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ad_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<Role>();

    public User() {

    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String account) {
        this.id = id;
        this.account = account;
    }

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ApproveRole getApproveRole() {
        return approveRole;
    }

    public void setApproveRole(ApproveRole approveRole) {
        this.approveRole = approveRole;
    }
}
