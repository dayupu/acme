package com.manage.kernel.jpa.entity;

import com.manage.kernel.jpa.base.EntityBase;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

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

    @Column(name = "watch_time")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime watchTime;

    @Column(name = "leader")
    private String leader;

    @Column(name = "captain")
    private String captain;

    @Column(name = "worker")
    private String worker;

    @Column(name = "phone")
    private String phone;

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

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

    public LocalDateTime getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(LocalDateTime watchTime) {
        this.watchTime = watchTime;
    }



}
