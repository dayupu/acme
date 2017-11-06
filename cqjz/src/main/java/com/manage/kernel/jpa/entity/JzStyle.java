package com.manage.kernel.jpa.entity;

import com.manage.kernel.jpa.base.EntityBase;

import com.manage.kernel.jpa.base.StatusBase;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/11/5.
 */
@Entity
@Table(name = "jz_style")
@SequenceGenerator(name = "seq_jz_style", sequenceName = "seq_jz_style", allocationSize = 1)
public class JzStyle extends StatusBase {

    @Id
    @Column(name = "number")
    private String number;

    @Column(name = "title")
    private String title;

    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "number")
    @OrderBy("sequence asc")
    private List<JzStyleLine> styleLines = new ArrayList<>();

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<JzStyleLine> getStyleLines() {
        return styleLines;
    }

    public void setStyleLines(List<JzStyleLine> styleLines) {
        this.styleLines = styleLines;
    }
}
