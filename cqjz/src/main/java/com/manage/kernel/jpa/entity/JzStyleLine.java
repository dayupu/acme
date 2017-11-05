package com.manage.kernel.jpa.entity;

import javax.persistence.*;

/**
 * Created by bert on 2017/11/5.
 */
@Entity
@Table(name = "jz_style_line")
@SequenceGenerator(name = "seq_jz_style_line", sequenceName = "seq_jz_style_line", allocationSize = 1)
public class JzStyleLine {

    @Id
    @GeneratedValue(generator = "seq_jz_style_line", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
