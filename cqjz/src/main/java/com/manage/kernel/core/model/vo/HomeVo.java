package com.manage.kernel.core.model.vo;

import com.manage.kernel.core.model.dto.NewsDto;
import com.manage.kernel.core.model.dto.SuperstarDto;
import com.manage.kernel.core.model.dto.WatchDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/10/27.
 */
public class HomeVo {

    /*值班人员*/
    private WatchDto watch = new WatchDto();
    /*星级民警*/
    private SuperstarDto superstar = new SuperstarDto();
    /*图片新闻*/
    private List<NewsVo> picNews = new ArrayList<>();
    /*警情快讯*/
    private List<NewsVo> jqkxNews = new ArrayList<>();
    /*队伍建设*/
    private List<NewsVo> dwjsNews = new ArrayList<>();
    /*部门动态*/
    private List<NewsVo> bmdtNews = new ArrayList<>();
    /*学习园地*/
    private List<NewsVo> xxydNews = new ArrayList<>();
    /*网海拾贝*/
    private List<NewsVo> whsbNews = new ArrayList<>();
    /*科技瞭望*/
    private List<NewsVo> kjlwNews = new ArrayList<>();

    public WatchDto getWatch() {
        return watch;
    }

    public void setWatch(WatchDto watch) {
        this.watch = watch;
    }

    public SuperstarDto getSuperstar() {
        return superstar;
    }

    public void setSuperstar(SuperstarDto superstar) {
        this.superstar = superstar;
    }

    public List<NewsVo> getPicNews() {
        return picNews;
    }

    public void setPicNews(List<NewsVo> picNews) {
        this.picNews = picNews;
    }

    public List<NewsVo> getJqkxNews() {
        return jqkxNews;
    }

    public void setJqkxNews(List<NewsVo> jqkxNews) {
        this.jqkxNews = jqkxNews;
    }

    public List<NewsVo> getDwjsNews() {
        return dwjsNews;
    }

    public void setDwjsNews(List<NewsVo> dwjsNews) {
        this.dwjsNews = dwjsNews;
    }

    public List<NewsVo> getBmdtNews() {
        return bmdtNews;
    }

    public void setBmdtNews(List<NewsVo> bmdtNews) {
        this.bmdtNews = bmdtNews;
    }

    public List<NewsVo> getXxydNews() {
        return xxydNews;
    }

    public void setXxydNews(List<NewsVo> xxydNews) {
        this.xxydNews = xxydNews;
    }

    public List<NewsVo> getWhsbNews() {
        return whsbNews;
    }

    public void setWhsbNews(List<NewsVo> whsbNews) {
        this.whsbNews = whsbNews;
    }

    public List<NewsVo> getKjlwNews() {
        return kjlwNews;
    }

    public void setKjlwNews(List<NewsVo> kjlwNews) {
        this.kjlwNews = kjlwNews;
    }
}
