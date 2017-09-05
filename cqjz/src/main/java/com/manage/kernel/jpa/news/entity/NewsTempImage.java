package com.manage.kernel.jpa.news.entity;

import com.manage.kernel.jpa.news.base.TempFile;
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
@Table(name = "news_temp_image")
@SequenceGenerator(name = "seq_news_temp_image", sequenceName = "seq_news_temp_image")
public class NewsTempImage extends TempFile{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_news_temp_image")
    private Long id;

    @Column(name = "news_id", insertable = false, updatable = false)
    private Long newsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "size")
    private Long size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getOriginName() {
        return originName;
    }

    @Override
    public void setOriginName(String originName) {
        this.originName = originName;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public Long getSize() {
        return size;
    }

    @Override
    public void setSize(Long size) {
        this.size = size;
    }
}
