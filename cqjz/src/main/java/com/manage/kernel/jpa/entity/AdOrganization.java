package com.manage.kernel.jpa.entity;

import com.manage.kernel.jpa.base.EntityBase;

import javax.persistence.*;
import java.util.List;

/**
 * Created by bert on 17-10-11.
 */
@Entity
@Table(name = "ad_organization")
public class AdOrganization extends EntityBase {

    @Id
    private String code;

    @Column(name = "parent_code", insertable = false, updatable = false)
    private String parentCode;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_code")
    private AdOrganization parent;

    @Column(name = "sequence", length = 3)
    private int sequence = 0;

    @Column(name = "level", length = 3)
    private int level = 0;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "parent")
    @OrderBy("sequence asc")
    private List<AdOrganization> childrens;

    public AdOrganization() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public AdOrganization getParent() {
        return parent;
    }

    public void setParent(AdOrganization parent) {
        this.parent = parent;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
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

    public List<AdOrganization> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<AdOrganization> childrens) {
        this.childrens = childrens;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
