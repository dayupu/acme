package com.manage.kernel.jpa.entity;

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
@Table(name = "news_attach")
@SequenceGenerator(name = "seq_news_attach", sequenceName = "seq_news_attach")
public class NewsAttach {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_news_attach")
    private Long id;

    @Column(name = "news_id", insertable = false, updatable = false)
    private Long newsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNewsId() {
        return newsId;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
