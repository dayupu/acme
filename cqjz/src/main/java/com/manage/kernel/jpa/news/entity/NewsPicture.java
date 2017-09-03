package com.manage.kernel.jpa.news.entity;

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
 * Created by bert on 2017/9/3.
 */
@Entity
@Table(name = "news_picture")
@SequenceGenerator(name = "seq_news_picture", sequenceName = "seq_news_picture")
public class NewsPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_news_picture")
    private Long id;

    @Column(name = "news_id", insertable = false, updatable = false)
    private Long newsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @Column(name = "extension")
    private String extension;

    @Column(name = "file_size")
    private Long fileSize;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
