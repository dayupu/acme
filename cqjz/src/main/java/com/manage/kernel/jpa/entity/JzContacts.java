package com.manage.kernel.jpa.entity;

import com.manage.kernel.jpa.base.EntityBase;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

/**
 * Created by bert on 17-10-12.
 */
@Entity
@Table(name = "jz_contacts")
@SequenceGenerator(name = "seq_jz_contacts", sequenceName = "seq_jz_contacts", allocationSize = 1, initialValue = 10)
public class JzContacts extends EntityBase {

    @Id
    @GeneratedValue(generator = "seq_jz_contacts", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "version")
    private Integer version;

    @Column(name = "is_history")
    private Boolean isHistory;

    public JzContacts() {

    }

    public JzContacts(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getHistory() {
        return isHistory;
    }

    public void setHistory(Boolean history) {
        isHistory = history;
    }
}
