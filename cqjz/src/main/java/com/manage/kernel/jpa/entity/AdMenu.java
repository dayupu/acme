package com.manage.kernel.jpa.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ad_menu")
@SequenceGenerator(name = "seq_ad_menu", sequenceName = "seq_ad_menu", allocationSize = 1, initialValue = 100)
public class AdMenu {

    @Id
    @GeneratedValue(generator = "seq_ad_menu", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "parent_id", insertable = false, updatable = false)
    private Long parentId;

    @Column(name = "sequence", length = 3)
    private int sequence = 0;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "url", length = 100)
    private String url;

    @Column(name = "level", length = 2)
    private int level = 0;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private AdMenu parent;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "parent")
    @OrderBy("sequence asc")
    private List<AdMenu> childrens;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ad_role_menu", joinColumns = @JoinColumn(name = "menu_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<AdRole> roles = new ArrayList<AdRole>();

    public AdMenu() {

    }

    public AdMenu(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AdMenu getParent() {
        return parent;
    }

    public void setParent(AdMenu parent) {
        this.parent = parent;
    }

    public List<AdMenu> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<AdMenu> childrens) {
        this.childrens = childrens;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<AdRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AdRole> roles) {
        this.roles = roles;
    }
}
