package com.manage.kernel.jpa.entity;

import com.manage.kernel.jpa.base.EntityBase;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by bert on 17-10-12.
 */
@Entity
@Table(name = "jz_watch")
@SequenceGenerator(name = "seq_jz_watch", sequenceName = "seq_jz_watch", allocationSize = 1)
public class JzWatch extends EntityBase {

    @Id
    @GeneratedValue(generator = "seq_jz_watch", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "leader")
    private String leader;

    @Column(name = "worker")
    private String worker;

    @Column(name = "phone")
    private String phone;

    @Column(name = "active")
    private Boolean active = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
