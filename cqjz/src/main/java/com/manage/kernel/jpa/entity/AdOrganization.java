package com.manage.kernel.jpa.entity;

import com.manage.kernel.jpa.base.EntityBase;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by bert on 17-10-11.
 */
@Entity
@Table(name = "ad_organization")
@SequenceGenerator(name = "seq_ad_organization", sequenceName = "seq_ad_organization", allocationSize = 1)
public class AdOrganization extends EntityBase {

    @Id
    @GeneratedValue(generator = "seq_ad_organization", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "parent_id", insertable = false, updatable = false)
    private Long parentId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private AdOrganization parent;

    @Column(name = "sequence", length = 3)
    private int sequence = 0;

    @Column(name = "level", length = 2)
    private int level = 0;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
}
