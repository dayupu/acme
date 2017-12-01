package com.manage.kernel.core.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 17-12-1.
 */
public class TopicVo {

    private Long code;
    private String name;
    private List<NewsVo> newses = new ArrayList<>();

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public List<NewsVo> getNewses() {
        return newses;
    }

    public void setNewses(List<NewsVo> newses) {
        this.newses = newses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
