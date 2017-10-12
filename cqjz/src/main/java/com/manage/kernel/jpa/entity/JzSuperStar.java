package com.manage.kernel.jpa.entity;

import com.manage.kernel.jpa.base.EntityBase;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by bert on 17-10-12.
 */
@Entity
@Table(name = "jz_super_star")
@SequenceGenerator(name = "seq_jz_super_star", sequenceName = "seq_jz_super_star", allocationSize = 1)
public class JzSuperStar extends EntityBase {

    @Id
    @GeneratedValue(generator = "seq_jz_super_star", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_name")
    private String photoName;

    @Column(name = "story")
    private String story;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
