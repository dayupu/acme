package com.manage.kernel.jpa.entity;

import com.manage.kernel.jpa.base.StatusBase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
@Table(name = "ad_role")
@SequenceGenerator(name = "seq_role", sequenceName = "seq_role", allocationSize = 1)
public class AdRole extends StatusBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role")
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "ad_role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_code"))
    private List<AdPermission> permissions = new ArrayList<AdPermission>();

    @ManyToMany(mappedBy = "roles")
    private List<AdUser> users = new ArrayList<AdUser>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ad_role_menu", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private List<AdMenu> menus = new ArrayList<AdMenu>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AdPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<AdPermission> permissions) {
        this.permissions = permissions;
    }

    public List<AdUser> getUsers() {
        return users;
    }

    public void setUsers(List<AdUser> users) {
        this.users = users;
    }

    public List<AdMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<AdMenu> menus) {
        this.menus = menus;
    }
}
