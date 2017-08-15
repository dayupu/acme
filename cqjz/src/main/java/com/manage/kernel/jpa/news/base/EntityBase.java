package com.manage.kernel.jpa.news.base;

import com.manage.kernel.jpa.news.entity.User;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EntityBase {

    @Column(name = "created_by", insertable = false, updatable = false)
    private Long createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdUser;

    @Column(name = "created_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime createdAt;

    @Column(name = "updated_by", insertable = false, updatable = false)
    private Long udpatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedUser;

    @Column(name = "updated_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime updatedAt;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUdpatedBy() {
        return udpatedBy;
    }

    public void setUdpatedBy(Long udpatedBy) {
        this.udpatedBy = udpatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public String getCreatedUserName() {
        if (getCreatedBy() == null) {
            return null;
        }
        return getCreatedUser().getName();
    }

    public String getUpdatedUserName() {
        if (getUpdatedUser() == null) {
            return null;
        }
        return getUpdatedUser().getName();
    }
}
