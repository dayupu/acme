package com.manage.kernel.jpa.entity;

import com.manage.base.database.enums.ApproveRole;
import com.manage.base.database.enums.Gender;
import com.manage.kernel.jpa.base.StatusBase;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "ad_user")
@SequenceGenerator(name = "seq_ad_user", sequenceName = "seq_ad_user")
public class AdUser extends StatusBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ad_user")
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
    private List<AdPermission> permissions = new ArrayList<AdPermission>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ad_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<AdRole> roles = new ArrayList<AdRole>();

    @Column(name = "organ_id", insertable = false, updatable = false)
    private Long organId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_id")
    private AdOrganization organ;

    public AdUser() {

    }

    public AdUser(Long id) {
        this.id = id;
    }

    public AdUser(Long id, String account) {
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

    public List<AdPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<AdPermission> permissions) {
        this.permissions = permissions;
    }

    public List<AdRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AdRole> roles) {
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

    public Long getOrganId() {
        return organId;
    }

    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    public AdOrganization getOrgan() {
        return organ;
    }

    public String getOrganName() {
        if (organ == null) {
            return null;
        }
        return organ.getName();
    }

    public void setOrgan(AdOrganization organ) {
        this.organ = organ;
    }
}
