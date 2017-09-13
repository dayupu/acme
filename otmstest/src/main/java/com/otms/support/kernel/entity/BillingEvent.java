package com.otms.support.kernel.entity;

import com.otms.support.supplier.database.enums.APISource;
import com.otms.support.supplier.database.enums.Direction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 17-9-11.
 */

@Entity
@Table(name = "billing_event")
@SequenceGenerator(name = "seq_billing_event", sequenceName = "seq_billing_event", allocationSize = 1)
public class BillingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_billing_event")
    private Long id;

    @Column(name = "mark")
    private String mark;

    @Column(name = "created_on")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime createdOn;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
